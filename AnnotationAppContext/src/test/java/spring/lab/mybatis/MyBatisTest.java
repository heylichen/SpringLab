package spring.lab.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.ParamNameResolver;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.lab.AppConfig;
import spring.lab.data.mybatis.dao.Car;
import spring.lab.data.mybatis.dao.mapper.CarMapper;
import spring.lab.data.mybatis.service.CarDbService;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MyBatisTest {
  private List<Car> cars;
  private List<String> carNos;
  private AnnotationConfigApplicationContext appContext;

  @Before
  public void setUp() throws NoSuchMethodException {
    appContext = new AnnotationConfigApplicationContext(AppConfig.class);
    //prepare test data
    carNos = new ArrayList<>();
    cars = new ArrayList<>();
    for (int i = 0; i < 20; i++) {
      String carNo = String.format("A%03d", i);
      carNos.add(carNo);
      cars.add(new Car(carNo));
    }
    log.info("init data: {}", carNos);
  }

  @Test
  public void testTxMapperApi() {
    CarDbService carDbService = appContext.getBean(CarDbService.class);
    carDbService.deleteByCarNos(carNos);
    carDbService.insert(cars);
    System.out.println();

    List<Car> page = carDbService.pageQuery(10, 10);
    System.out.println();
  }

  @Test
  public void testMapperApi() {
    CarMapper carMapper = appContext.getBean(CarMapper.class);
    carMapper.deleteByCarNos(carNos);
    carMapper.insert(cars);

    List<Car> page = carMapper.pageQuery(10, 10);
    viewCarNos(page);
  }

  @Test
  public void testMyBatisSessionApi() throws NoSuchMethodException {
    SqlSessionFactory sqlSessionFactory = appContext.getBean(SqlSessionFactory.class);
    Method mapperMethod = CarMapper.class.getDeclaredMethod("pageQuery", Integer.class, Integer.class);
    ParamNameResolver pnr = new ParamNameResolver(sqlSessionFactory.getConfiguration(), mapperMethod);

    int limit = 10;
    int offset = 10;
    Object[] queryArgs = new Object[]{offset, limit};

    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      //sqlSession API accept only single parameter object, so need to convert if we have multiple args.
      Object param = pnr.getNamedParams(queryArgs);
      List<Car> page = sqlSession.selectList("spring.lab.data.mybatis.dao.mapper.CarMapper.pageQuery", param);
      viewCarNos(page);
    }
  }

  private void viewCarNos(List<Car> page) {
    List<String> carNos = page.stream().map(Car::getCarNo).toList();
    System.out.println(String.join(", ", carNos));
  }
}
