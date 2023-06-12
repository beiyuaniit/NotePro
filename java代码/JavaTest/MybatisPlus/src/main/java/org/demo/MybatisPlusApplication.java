package org.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: beiyuan
 * @className: MybatisPlusApplication
 * @date: 2022/8/10  17:00
 */

@MapperScan("org.demo.mapper")
@SpringBootApplication
public class MybatisPlusApplication {
}
