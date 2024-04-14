package spring.lab.transaction;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.lab.AppConfig;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TransactionTest {

  @Test
  public void testTransactionTemplate() {
//context refresh
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);

    UserService userService = appContext.getBean("userService", UserService.class);

    Set<Integer> userIds = new HashSet<>(Arrays.asList(1, 2));
    initData(userService, userIds);
    System.out.println("before transfer");
    listUserAccounts(userService, userIds);

    BigDecimal delta = new BigDecimal(10);
    try {
      //关注这个方法
      userService.transferByTm(1, 2, delta);
    } finally {
      System.out.println("after transfer " + delta);
      listUserAccounts(userService, userIds);
    }
  }

  @Test
  public void testTransactionManagerRollback() {
//context refresh
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);

    UserService userService = appContext.getBean("userService", UserService.class);

    Set<Integer> userIds = new HashSet<>(Arrays.asList(1, 2));
    initData(userService, userIds);
    System.out.println("before transfer");
    listUserAccounts(userService, userIds);

    BigDecimal delta = new BigDecimal(10);
    try {
      //关注这个方法
      userService.transferByTm(1, 3, delta);
    } finally {
      System.out.println("after transfer " + delta);
      listUserAccounts(userService, userIds);
    }
  }


  private static void initData(UserService userService, Set<Integer> userIds) {
    userService.deleteByUserIds(userIds);
    userIds.forEach((id -> userService.insert(id, new BigDecimal(100))));
  }

  private static void listUserAccounts(UserService userService, Set<Integer> userIds) {
    List<UserAccount> accounts = userService.queryByUserIds(userIds);
    StringBuilder sb = new StringBuilder();
    for (UserAccount account : accounts) {
      sb.append("userId: ").append(account.getUserId()).append(", balance: ").append(account.getBalance()).append("\n");
    }
    System.out.println(sb);
  }
}