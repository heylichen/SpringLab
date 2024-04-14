package spring.lab.transaction;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.lab.AppConfig;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TransactionTest {
  private final Set<Integer> USER_IDS = new HashSet<>(Arrays.asList(1, 2));
  private UserService userService;

  @Before
  public void setUp() {
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);
    userService = appContext.getBean("userService", UserService.class);
    initData(USER_IDS);
    System.out.println("init data ... ");
    listUserAccounts(USER_IDS);
  }

  @Test
  public void testTransactionManager() {
    BigDecimal delta = new BigDecimal(10);
    try {
      userService.transferByTm(1, 2, delta);
    } finally {
      System.out.println("after transfer " + delta);
      listUserAccounts(USER_IDS);
    }
  }

  @Test
  public void testTransactionManagerRollback() {
    BigDecimal delta = new BigDecimal(10);
    try {
      userService.transferByTm(1, 3, delta);
    } finally {
      System.out.println("after transfer " + delta);
      listUserAccounts(USER_IDS);
    }
  }

  @Test
  public void testTransactionTemplate() {
    BigDecimal delta = new BigDecimal(10);
    try {
      userService.transferByTemplate(1, 2, delta);
    } finally {
      System.out.println("after transfer " + delta);
      listUserAccounts(USER_IDS);
    }
  }

  @Test
  public void testTransactionTemplateRollback() {
    BigDecimal delta = new BigDecimal(10);
    try {
      userService.transferByTemplate(1, 3, delta);
    } finally {
      System.out.println("after transfer " + delta);
      listUserAccounts(USER_IDS);
    }
  }

  private void initData(Set<Integer> userIds) {
    userService.deleteByUserIds(userIds);
    userIds.forEach((id -> userService.insert(id, new BigDecimal(100))));
  }

  private void listUserAccounts(Set<Integer> userIds) {
    List<UserAccount> accounts = userService.queryByUserIds(userIds);
    StringBuilder sb = new StringBuilder();
    for (UserAccount account : accounts) {
      sb.append("userId: ").append(account.getUserId()).append(", balance: ").append(account.getBalance()).append("\n");
    }
    System.out.println(sb);
  }
}