package com.beiyuan.vod;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * @author: beiyuan
 * @date: 2022/11/1  22:06
 */

@SpringBootApplication
@ComponentScans(@ComponentScan("com.beiyuan"))
@MapperScan("com.beiyuan.vod.mapper")
public class ServiceVodApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceVodApplication.class,args);

    }
}
