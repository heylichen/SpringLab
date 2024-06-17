package spring.lab.jmx;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class Sample implements SampleMBean {
  private String name;
  private String age;

  @Override
  public void report(String message) {
    log.info("name:{}, age:{}， inputMessage:{}", name, age, message);
  }
}
