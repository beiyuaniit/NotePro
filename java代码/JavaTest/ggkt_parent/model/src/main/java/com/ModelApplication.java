package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: beiyuan
 * @date: 2022/11/1  14:46
 */
@SpringBootApplication
@MapperScan("com.mapper")
public class ModelApplication {
    public static void main(String[] args) {
        SpringApplication.run(ModelApplication.class,args);
    }
}
