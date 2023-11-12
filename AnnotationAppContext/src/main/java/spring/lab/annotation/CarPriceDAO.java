package spring.lab.annotation;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class CarPriceDAO {
  private Map<String, Double> modelPrice;

  @PostConstruct
  public void init() {
    modelPrice = new HashMap<>();
    modelPrice.put("Model3", 10d);
    modelPrice.put("ModelY", 20d);
  }

  public Double getPrice(String model) {
    return model == null ? null : modelPrice.get(model);
  }
}
