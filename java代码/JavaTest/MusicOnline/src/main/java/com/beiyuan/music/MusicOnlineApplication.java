package com.beiyuan.music;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.beiyuan.music.mapper")
@EnableSwagger2
public class MusicOnlineApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicOnlineApplication.class, args);
    }

}
