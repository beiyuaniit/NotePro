package demo.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

/**
 * @author: beiyuan
 * @className: Student
 * @date: 2022/8/6  21:31
 */
@Data
@ToString
@TableName("st")
public class Student {
    private Integer id;
    private String name;
    private String email;
    private Integer age;
}
