package demo;

import demo.bean.User;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author: beiyuan
 * @className: MainApplication
 * @date: 2022/7/16  0:01
 */
/*
主程序类
@SpringApplication:这是一个springboot应用，有一个就行了
 */

@MapperScan("demo.mapper")
@ServletComponentScan(basePackages = "demo")
@SpringBootApplication
public class MainApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext ioc=SpringApplication.run(MainApplication.class,args);

        String[] names=ioc.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }

        User user=ioc.getBean("user01",User.class);
        System.out.println(user.getClass());

        boolean existsUserXml=ioc.containsBean("myUserXml");
        System.out.println(existsUserXml);

    }
}
