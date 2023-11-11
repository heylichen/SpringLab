package spring.lab.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Driver {
  public static void main(String[] args) {
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);
    Hello hello = appContext.getBean("hello", Hello.class);
    System.out.println(hello.say("something"));;
  }
}
