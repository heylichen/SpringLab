package spring.lab.transaction;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.lab.AppConfig;
import spring.lab.data.mybatis.dao.Car;
import spring.lab.data.mybatis.service.CarDbService;

import java.util.ArrayList;
import java.util.List;

public class SingleTxTest {

  /**
   * test release connection.
   * Mapper and Spring tx
   */
  @Test
  public void testReleaseConnection() {
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);
    CarDbService cs = appContext.getBean("carDbService", CarDbService.class);

    List<Car> cars = new ArrayList<>();
    for (int i = 0; i < 1; i++) {
      String carNo = String.format("A%03d", i);
      cars.add(new Car(carNo));
    }
    cs.insert2(cars);
  }

}
