package org.workqueues03.轮询消费;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.Utils.RabbitMqUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: beiyuan
 * @className: Producer
 * @date: 2022/9/27  20:03
 */
public class Consume {
    public static final String QUEUE_NAME="hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel= RabbitMqUtils.getChannel();

        DeliverCallback deliverCallback=(consumerTag,message)->{
            System.out.println("接收到消息："+new String(message.getBody()));
        };
        CancelCallback cancelCallback=(consumerTag)->{
            System.out.println("取消消息");
        };


        System.out.println("C2等待接收消息...");
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);

    }
}
