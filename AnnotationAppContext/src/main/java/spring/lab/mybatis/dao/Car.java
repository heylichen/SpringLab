package spring.lab.mybatis.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * car
 */
@Data
@NoArgsConstructor
public class Car implements Serializable {
    private BigInteger id;

    private String carNo;

    public Car(String carNo) {
        this.carNo = carNo;
    }
    private static final long serialVersionUID = 1L;
}