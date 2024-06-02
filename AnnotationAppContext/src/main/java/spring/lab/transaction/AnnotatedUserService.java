package spring.lab.transaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.math.BigDecimal;

/**
 * 参考 官方文档
 * Spring 编程式事务管理
 * <a href="https://docs.spring.io/spring-framework/reference/data-access/transaction/programmatic.html">...</a>
 *
 * REQUIRED        支持使用当前事务，如果当前事务不存在，创建一个新事务。
 * REQUIRES_NEW    创建一个新事务，如果当前事务存在，把当前事务挂起。
 * NESTED          嵌套事务
 *
 * SUPPORTS        有就参加，无也不影响。
 * NOT_SUPPORTED   有没有都不参加事务。 无事务执行，如果当前事务存在，把当前事务挂起
 *
 * MANDATORY       为强制，支持使用当前事务，如果当前事务不存在，则抛出Exception。
 * NEVER           无事务执行，如果当前有事务则抛出Exception。
 */
@Component
public class AnnotatedUserService {
  @Autowired
  private UserDao userDao;
  @Autowired

  private AnnotatedUserService self;
  @Transactional(propagation = Propagation.REQUIRED)
  public void afterCommit() {
    TransactionSynchronizationManager.registerSynchronization(newSynchronization());
    self.innerRequired(false);
  }
  @Transactional(propagation = Propagation.REQUIRED)
  public void afterRollback() {
    TransactionSynchronizationManager.registerSynchronization(newSynchronization());
    self.innerRequired(true);
  }

  private TransactionSynchronization newSynchronization() {
    return new TransactionSynchronizationAdapter() {
      @Override
      public void afterCommit() {
        System.out.println("---> afterCommit");
      }

      @Override
      public void afterCompletion(int status) {
        System.out.println("---> afterCompletion, status=" + status);
      }
    };
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void globalRollback() {
    //回滚
    self.innerRequired(false);
    //不回滚
    self.innerRequiresNew(false);
    //回滚
    self.innerNested(false);
    //回滚
    userDao.update(4, new BigDecimal(1000));
    throw new IllegalArgumentException("exception thrown ");
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void innerRequiredRollback() {
    //回滚
    userDao.update(4, new BigDecimal(1000));
    self.innerRequired(true);
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void innerRequiresNewRollback() {
    //回滚
    userDao.update(4, new BigDecimal(1000));
    //回滚
    self.innerRequiresNew(true);
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void innerNestedRollback() {
    //回滚
    self.innerNested(true);
    //回滚
    userDao.update(4, new BigDecimal(1000));
  }

  //试图通过try catch, 来产生内部调用rollback,不导致外部事务rollback的效果。
  //但实际没有达到预期效果
  // innerRequired 抛出的异常，先被Spring tx AOP处理了(整个事务标记为rollbackOnly)，
  // 然后被catchInnerRequiredRollback() catch
  @Transactional(propagation = Propagation.REQUIRED)
  public void catchInnerRequiredRollback() {
    //回滚
    userDao.update(4, new BigDecimal(1000));
    try {
      self.innerRequired(true);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void catchInnerRequiresNewRollback() {
    //不回滚
    userDao.update(4, new BigDecimal(1000));
    try {
      //回滚
      self.innerRequiresNew(true);
    } catch (Exception e) {
      //阻止异常传播到外层事务
      System.out.println("error caught : " + e.getMessage());
    }
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void catchInnerNestedRollback() {
    //不回滚
    userDao.update(4, new BigDecimal(1000));
    //回滚
    try {
      self.innerNested(true);
    } catch (Exception e) {
      //阻止异常传播到外层事务
      System.out.println("error caught : " + e.getMessage());
    }
  }
  // ----------- tool methods ------------------
  @Transactional(propagation = Propagation.REQUIRED)
  public void innerRequired(boolean rollback) {
    userDao.update(1, new BigDecimal(1000));
    if (rollback) {
      throw new IllegalArgumentException("exception for testing");
    }
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void innerRequiresNew(boolean rollback) {
    userDao.update(2, new BigDecimal(1000));
    if (rollback) {
      throw new IllegalArgumentException("exception for testing");
    }
  }

  @Transactional(propagation = Propagation.NESTED)
  public void innerNested(boolean rollback) {
    userDao.update(3, new BigDecimal(1000));
    if (rollback) {
      throw new IllegalArgumentException("exception for testing");
    }
  }

}
