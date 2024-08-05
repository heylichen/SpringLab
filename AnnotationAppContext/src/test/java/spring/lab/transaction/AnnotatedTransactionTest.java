package spring.lab.transaction;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.lab.AppConfig;
import spring.lab.data.transaction.AnnotatedUserService;
import spring.lab.data.transaction.UserAccount;
import spring.lab.data.transaction.UserDao;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnnotatedTransactionTest {
  private final Set<Integer> USER_IDS = new HashSet<>(Arrays.asList(1, 2, 3, 4));
  private AnnotatedUserService userService;
  private UserDao userDao;
  @Before
  public void setUp() {
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);
    userService = appContext.getBean("annotatedUserService", AnnotatedUserService.class);
    userDao = appContext.getBean("userDao", UserDao.class);
    initData(USER_IDS);
    System.out.println("init data ... ");
    listUserAccounts(USER_IDS);
  }

  @Test
  public void testGlobalRollback() {
    try {
      userService.globalRollback();
    } finally {
      listUserAccounts(USER_IDS);
    }
  }

  @Test
  public void innerRequiredRollback() {
    try {
      userService.innerRequiredRollback();
    } finally {
      listUserAccounts(USER_IDS);
    }
  }

  @Test
  public void innerRequiresNewRollback() {
    try {
      userService.innerRequiresNewRollback();
    } finally {
      listUserAccounts(USER_IDS);
    }
  }

  @Test
  public void innerNestedRollback() {
    try {
      userService.innerNestedRollback();
    } finally {
      listUserAccounts(USER_IDS);
    }
  }


  @Test
  public void catchInnerRequiredRollback() {
    try {
      userService.catchInnerRequiredRollback();
    } finally {
      listUserAccounts(USER_IDS);
    }
  }

  @Test
  public void catchInnerRequiresNewRollback() {
    try {
      userService.catchInnerRequiresNewRollback();
    } finally {
      listUserAccounts(USER_IDS);
    }
  }

  @Test
  public void catchInnerNestedRollback() {
    try {
      userService.catchInnerNestedRollback();
    } finally {
      listUserAccounts(USER_IDS);
    }
  }


  @Test
  public void testAfterCommit() {
    try {
      userService.afterCommit();
    } finally {
      listUserAccounts(USER_IDS);
    }
  }


  @Test
  public void testAfterRollback() {
    try {
      userService.afterRollback();
    } finally {
      listUserAccounts(USER_IDS);
    }
  }

  private void initData(Set<Integer> userIds) {
    userDao.deleteByUserIds(userIds);
    userIds.forEach((id -> userDao.insert(id, new BigDecimal(100))));
  }

  private void listUserAccounts(Set<Integer> userIds) {
    List<UserAccount> accounts = userDao.queryByUserIds(userIds);
    StringBuilder sb = new StringBuilder();
    for (UserAccount account : accounts) {
      sb.append("userId: ").append(account.getUserId()).append(", balance: ").append(account.getBalance()).append("\n");
    }
    System.out.println(sb);
  }
}