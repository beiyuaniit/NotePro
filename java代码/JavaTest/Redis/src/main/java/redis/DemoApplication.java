package redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
public class DemoApplication {


    public static void main(String[] args) {

        System.out.println("hello");
        SpringApplication.run(DemoApplication.class, args);
    }

}
