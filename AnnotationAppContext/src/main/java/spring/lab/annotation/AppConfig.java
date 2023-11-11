package spring.lab.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("spring.lab.annotation")
public class AppConfig {

  @Bean
  public Hello hello() {
    Person p = new Person("Zhangsan");
    Hello hello = new Hello();
    hello.setName("Hello");
    return hello;
  }
}
