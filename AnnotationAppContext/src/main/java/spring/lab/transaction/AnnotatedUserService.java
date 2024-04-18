package spring.lab.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
public class AnnotatedUserService {
  @Autowired
  private UserDao userDao;
  @Autowired
  private AnnotatedUserService self;

  @Transactional(propagation = Propagation.REQUIRED)
  public void transferOuter() {
    self.transferInnerRequired();
    self.transferInnerRequiresNew();
    self.transferInnerNested();
    self.transferInnerRequired2();
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void transferInnerRequired() {
    userDao.update(1, new BigDecimal(1000));
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void transferInnerRequiresNew() {
    userDao.update(2, new BigDecimal(1000));
  }

  @Transactional(propagation = Propagation.NESTED)
  public void transferInnerNested() {
    userDao.update(3, new BigDecimal(1000));
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void transferInnerRequired2() {
    throw new IllegalArgumentException("测试");
  }
}
