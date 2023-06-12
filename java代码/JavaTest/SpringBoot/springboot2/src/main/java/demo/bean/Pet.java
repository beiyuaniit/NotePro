package demo.bean;

/**
 * @author: beiyuan
 * @className: Pet
 * @date: 2022/7/18  15:04
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pet {
    private String name;
}
