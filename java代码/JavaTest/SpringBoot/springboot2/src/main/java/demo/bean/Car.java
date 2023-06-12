package demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


/**
 * @author: beiyuan
 * @className: Car
 * @date: 2022/7/18  17:29
 */
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor

@Component
@PropertySource("classpath:car.properties")
@ConfigurationProperties(prefix = "car1")
public class Car {
    private String brand;
    private Integer price;
}
