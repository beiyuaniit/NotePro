package demo.actuator.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: beiyuan
 * @className: MyComHealthIndicator1
 * @date: 2022/8/9  10:54
 */
@Component
public class MyComHealthIndicator1 extends AbstractHealthIndicator {
    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        Map<String, Object> map=new HashMap<>();
        if(true){
            builder.status(Status.UP);
            map.put("count",1);
            map.put("msg",101);
        }else {
            builder.status(Status.OUT_OF_SERVICE);
            map.put("error","连接超时");
            map.put("msg",202);
        }
        builder.withDetail("code",100).withDetails(map);
    }
}
