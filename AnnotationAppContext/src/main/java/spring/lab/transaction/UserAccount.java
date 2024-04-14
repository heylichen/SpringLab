package spring.lab.transaction;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class UserAccount implements Serializable {
  private Integer userId;
  private BigDecimal balance;

  public UserAccount(Integer userId, BigDecimal balance) {
    this.userId = userId;
    this.balance = balance;
  }
}
