package spring.lab.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.lab.annotation.config.AppConfig;

public class Driver {
  public static void main(String[] args) {
    //context refresh
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);

    Hello hello = appContext.getBean("hello", Hello.class);
    System.out.println(hello.say("something"));;

    CarService carService = appContext.getBean("carService", CarService.class);
    System.out.println(carService.getPrice("Model3"));
  }
}
