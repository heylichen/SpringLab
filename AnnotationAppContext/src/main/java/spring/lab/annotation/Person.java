package spring.lab.annotation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Person {
  private String name;
  private Hello hello;

  public Person(String name) {
    this.name = name;
  }
}
