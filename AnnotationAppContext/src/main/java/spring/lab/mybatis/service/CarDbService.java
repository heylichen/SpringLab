package spring.lab.mybatis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.lab.mybatis.dao.Car;
import spring.lab.mybatis.dao.mapper.CarMapper;

import java.util.Collection;
import java.util.List;

@Component
public class CarDbService {
  @Autowired
  private CarMapper carMapper;

  public List<Car> pageQuery(Integer limit, Integer offset) {
    return carMapper.pageQuery(limit, offset);
  }

  public void insert(Collection<Car> cars) {
    carMapper.insert(cars);
  }

  public void deleteByCarNos(Collection<String> carNos) {
    carMapper.deleteByCarNos(carNos);
  }
}
