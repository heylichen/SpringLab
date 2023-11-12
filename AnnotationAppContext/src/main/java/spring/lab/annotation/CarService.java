package spring.lab.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CarService {
  @Autowired
  private CarPriceDAO carPriceDAO;

  public Double getPrice(String model) {
    Double price = carPriceDAO.getPrice(model);
    return price;
  }
}
