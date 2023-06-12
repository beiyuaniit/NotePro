package demo.config;

import ch.qos.logback.classic.db.DBHelper;
import demo.bean.Pet;
import demo.bean.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * @author: beiyuan
 * @className: MyConfig
 * @date: 2022/7/18  15:07
 */
@Import({DBHelper.class,User.class})
@Configuration(proxyBeanMethods = true)
@ImportResource("classpath:bean.xml")
public class MyConfig {

    @Bean
    public User user01(){

        return new User("zhuxi",32,tomcat01());
    }

    @Bean
    public Pet tomcat01(){
        return new Pet("quyuan");
    }
}
