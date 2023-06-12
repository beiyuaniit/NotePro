package demo.actuator.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

/**
 * @author: beiyuan
 * @className: DockerEndpoint
 * @date: 2022/8/9  14:19
 */
@Component
@Endpoint(id = "container")
public class DockerEndpoint {

    //下面都是端点的方法，调用相应的方法可以执行相关操作，可用JMX来调用

    //数据从读方法返回。返回值无所谓，无参
    @ReadOperation
    public Map getDockerInfo(){
        return Collections.singletonMap("info","docker started...");
    }

    @WriteOperation
    private void restartDocker(){
        System.out.println("docker restarted....");
    }

}