package org.workqueues03.不公平分发;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.Utils.RabbitMqUtils;

import java.io.IOException;

import java.util.concurrent.TimeoutException;

/**
 * @author: beiyuan
 * @className: Consumer
 * @date: 2022/9/29  15:22
 */
public class Consumer {
    public static final String QUEUE_NAME="hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel= RabbitMqUtils.getChannel();
        System.out.println("C2预处理一个...较");

        int prefetchCount=5;
        channel.basicQos(prefetchCount);
        DeliverCallback deliverCallback=(consumerTag,message)->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("收到了："+new String(message.getBody()));
            channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
        };

        CancelCallback cancelCallback=(consumerTag)->{
            System.out.println("未成功："+consumerTag);
        };

        channel.basicConsume(QUEUE_NAME,false,deliverCallback,cancelCallback);
    }
}
