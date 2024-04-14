package spring.lab.transaction;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Setter
@Component
public class UserDao {
  @Autowired
  private JdbcTemplate jdbcTemplate;
  private final static RowMapper<UserAccount> ROW_MAPPER = new RowMapper<UserAccount>() {
    @Override
    public UserAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
      Integer uId = rs.getInt("user_id");
      BigDecimal balance = rs.getBigDecimal("balance");
      return new UserAccount(uId, balance);
    }
  };

  public void deleteByUserIds(Set<Integer> userIds) {
    int[] argTypes = genUserIdArgTypes(userIds.size());
    String placeholdersStr = genPlaceholders(userIds.size());
    jdbcTemplate.update("delete from user_account where user_id in (" + placeholdersStr + ")",
        userIds.toArray(), argTypes);
  }

  public List<UserAccount> queryByUserIds(Set<Integer> userIds) {
    int[] argTypes = genUserIdArgTypes(userIds.size());
    String placeholdersStr = genPlaceholders(userIds.size());

    return jdbcTemplate.query("select user_id,balance from user_account where user_id in (" + placeholdersStr + ")",
        userIds.toArray(), argTypes,
        ROW_MAPPER);
  }

  private int[] genUserIdArgTypes(int size) {
    int[] argTypes = new int[size];
    Arrays.fill(argTypes, Types.INTEGER);
    return argTypes;
  }

  private String genPlaceholders(int size) {
    String[] holders = new String[size];
    Arrays.fill(holders, "?");
    return String.join(",", holders);
  }

  public void insert(Integer userId, BigDecimal balance) {
    jdbcTemplate.update("insert into user_account(user_id,balance) values(?,?)", userId, balance);
  }

  public void transfer(Integer userAId, Integer userBId, BigDecimal delta) {
    addBalance(userAId, delta.negate());
    addBalance(userBId, delta);
  }

  private void addBalance(Integer userId, BigDecimal delta) {
    int affected = jdbcTemplate.update("update user_account set balance = balance + ? where user_id= ?", delta, userId);
    if (affected == 0) {
      throw new IllegalArgumentException("user not found " + userId);
    }
  }
}
