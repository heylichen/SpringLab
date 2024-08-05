package spring.lab.data.mybatis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import spring.lab.data.mybatis.dao.Car;
import spring.lab.data.mybatis.dao.mapper.CarMapper;

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

  @Transactional
  public void insert2(Collection<Car> cars) {
    carMapper.insert(cars);
  }
}
