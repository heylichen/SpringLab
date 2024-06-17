package spring.lab.data.transaction.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfig {
  @Value("${spring.datasource.springLab.url}")
  private String url;
  @Value("${spring.datasource.springLab.driver}")
  private String driver;
  @Value("${spring.datasource.springLab.username}")
  private String username;
  @Value("${spring.datasource.springLab.password}")
  private String password;

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource ds = new DriverManagerDataSource();
    ds.setDriverClassName(driver);
    ds.setUrl(url);
    ds.setUsername(username);
    ds.setPassword(password);
    return ds;
  }

  @Bean
  public JdbcTemplate jdbcTemplate(DataSource dataSource) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    // 注入 dataSource
    jdbcTemplate.setDataSource(dataSource);
    return jdbcTemplate;

  }

  // 创建事务管理器的对象
  @Bean
  public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
    DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
    transactionManager.setDataSource(dataSource);
    return transactionManager;
  }
}
