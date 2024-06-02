package spring.lab.mybatis;

import org.apache.ibatis.reflection.ParamNameResolver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.lab.AppConfig;
import spring.lab.mybatis.dao.Car;
import spring.lab.mybatis.dao.mapper.CarMapper;
import spring.lab.mybatis.service.CarDbService;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MyBatisTest {
  private List<Car> cars;
  private List<String> carNos;

  private CarDbService carDbService;
  private SqlSessionFactory sqlSessionFactory;
  private Configuration configuration;
  private ParamNameResolver carMapperPageQueryPR;

  @Before
  public void setUp() throws NoSuchMethodException {
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);
    carDbService = appContext.getBean(CarDbService.class);
    sqlSessionFactory = appContext.getBean(SqlSessionFactory.class);
    configuration = sqlSessionFactory.getConfiguration();
    Method pageQueryMethod = CarMapper.class.getDeclaredMethod("pageQuery", Integer.class, Integer.class);
    carMapperPageQueryPR = new ParamNameResolver(configuration, pageQueryMethod);
    System.out.println(pageQueryMethod.getName());
    //prepare test data
    carNos = new ArrayList<>();
    cars = new ArrayList<>();
    for (int i = 0; i < 20; i++) {
      String carNo = String.format("A%03d", i);
      carNos.add(carNo);
      cars.add(new Car(carNo));
    }
  }

  @Test
  public void testMapperApi() {
    carDbService.deleteByCarNos(carNos);
    carDbService.insert(cars);
    System.out.println();

    List<Car> page = carDbService.pageQuery(10, 10);
    System.out.println();
  }

  @Test
  public void testMyBatisSessionApi() {
    int limit = 10;
    int offset = 10;
    // ParamNameResolver carMapperPageQueryPR
    Object param = carMapperPageQueryPR.getNamedParams(new Object[]{limit, offset});

    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      //sqlSession API accept only single parameter object, so need to convert if we have multiple args.
      List<Car> page = sqlSession.selectList("spring.lab.mybatis.dao.mapper.CarMapper.pageQuery", param);
      System.out.println();
    }
  }
}
