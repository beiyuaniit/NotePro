package org.Exchanges05.Topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.Utils.RabbitMqUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: beiyuan
 * @className: Consumer01
 * @date: 2022/10/1  14:43
 */
public class Consumer02 {
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        DeliverCallback deliverCallback=(consumerTag,message)->{
            System.out.println("收到了："+new String(message.getBody()));
        };
        channel.basicConsume("Q2",true,deliverCallback,consumerTag->{});
    }
}
