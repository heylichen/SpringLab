package spring.lab;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import spring.lab.annotation.Hello;

@Configuration
@ComponentScan("spring.lab")
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class AppConfig {

  @Bean
  public Hello hello() {
    Hello hello = new Hello();
    hello.setName("Hello");
    return hello;
  }
}
