package spring.lab.mybatis.dao.mapper;


import org.apache.ibatis.annotations.Param;
import spring.lab.mybatis.dao.Car;

import java.util.Collection;
import java.util.List;

public interface CarMapper {

  List<Car> pageQuery(@Param("limit") Integer limit, @Param("offset") Integer offset);

  void insert(@Param("cars") Collection<Car> cars);

  void deleteByCarNos(@Param("carNos") Collection<String> carNos);
}