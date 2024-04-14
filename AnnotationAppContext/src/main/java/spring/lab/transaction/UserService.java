package spring.lab.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * 参考 官方文档
 * Spring 编程式事务管理
 * <a href="https://docs.spring.io/spring-framework/reference/data-access/transaction/programmatic.html">...</a>
 */
@Component
public class UserService {
  @Autowired
  private UserDao userDao;
  @Autowired
  private PlatformTransactionManager platformTxManager;

  private TransactionTemplate transactionTemplate;

  public void deleteByUserIds(Set<Integer> userIds) {
    getTransactionTemplate().execute(new TransactionCallbackWithoutResult() {
      @Override
      protected void doInTransactionWithoutResult(TransactionStatus status) {
        userDao.deleteByUserIds(userIds);
      }
    });
  }

  public List<UserAccount> queryByUserIds(Set<Integer> userIds) {
    return getTransactionTemplate().execute(new TransactionCallback<List<UserAccount>>() {
      @Override
      public List<UserAccount> doInTransaction(TransactionStatus status) {
        return userDao.queryByUserIds(userIds);
      }
    });
  }

  public void insert(Integer userId, BigDecimal delta) {
    userDao.insert(userId, delta);
  }

  public void transferByTm(Integer userAId, Integer userBId, BigDecimal delta) {
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

    TransactionStatus status = platformTxManager.getTransaction(def);
    try {
      doTransfer(userAId, userBId, delta);
    } catch (Exception ex) {
      platformTxManager.rollback(status);
      throw ex;
    }
    platformTxManager.commit(status);
  }

  public void transferByTemplate(Integer userAId, Integer userBId, BigDecimal delta) {
    getTransactionTemplate().execute(new TransactionCallbackWithoutResult() {
      @Override
      protected void doInTransactionWithoutResult(TransactionStatus status) {
        doTransfer(userAId, userBId, delta);
      }
    });
  }

  private TransactionTemplate getTransactionTemplate() {
    TransactionTemplate t = transactionTemplate;
    if (t == null) {
      t = new TransactionTemplate(platformTxManager);
      this.transactionTemplate = t;
    }
    return t;
  }

  private void doTransfer(Integer userAId, Integer userBId, BigDecimal delta) {
    userDao.transfer(userAId, userBId, delta);
  }
}
