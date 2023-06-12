package com.beiyuan.model.student;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author: beiyuan
 * @className: Student
 * @date: 2022/8/10  17:05
 */
@Data
@TableName("st")
public class Student {
    @TableId("id")
    private Integer id;

    private String name;
    private String email;
    private Integer age;
}
