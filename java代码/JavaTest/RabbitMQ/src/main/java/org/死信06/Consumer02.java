package org.死信06;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.Utils.RabbitMqUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: beiyuan
 * @className: Consumer01
 * @date: 2022/10/1  22:39
 */
public class Consumer02 {
    //接收正常队列的消息
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        DeliverCallback callback=(consumerTag,message)->{
            System.out.println("收到了正常的消息："+new String(message.getBody()));
        };
        channel.basicConsume(Producer.DEAD_QUEUE,true,callback,(consumTag)->{});
    }
}
