package spring.lab.annotation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import spring.lab.annotation.Hello;

@Configuration
@ComponentScan("spring.lab.annotation")
public class AppConfig {

  @Bean
  public Hello hello() {
    Hello hello = new Hello();
    hello.setName("Hello");
    return hello;
  }
}
