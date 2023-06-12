package redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    RedisTemplate<String, Object>redisTemplate;


    @Test
    void contextLoads() {
        System.out.println(redisTemplate);
        String str=(String)redisTemplate.opsForValue().get("k2");
        System.out.println(str);
    }

}
