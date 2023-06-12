package demo.actuator.info;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.Collections;


/**
 * @author: beiyuan
 * @className: AppInfoInfoContributor
 * @date: 2022/8/9  13:49
 */
@Component
public class AppInfoInfoContributor implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("msg","你好").
                withDetail("wel","com").
                withDetails(Collections.singletonMap("world",2));
    }
}
