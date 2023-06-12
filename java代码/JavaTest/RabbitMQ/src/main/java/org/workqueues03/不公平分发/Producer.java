package org.workqueues03.不公平分发;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import org.Utils.RabbitMqUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author: beiyuan
 * @className: Producer
 * @date: 2022/9/29  14:38
 */
public class Producer {
    public static final String QUEUE_NAME="hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel=RabbitMqUtils.getChannel();
        //有了就不用声明

        for(int i=0;i<30;i++){
            String message=i+"";
            channel.basicPublish("",QUEUE_NAME,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes(StandardCharsets.UTF_8));
        }
    }
}
