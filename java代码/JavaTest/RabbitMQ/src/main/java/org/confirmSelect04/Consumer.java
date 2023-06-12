package org.confirmSelect04;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.Utils.RabbitMqUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: beiyuan
 * @className: Consumer
 * @date: 2022/9/28  19:09
 */
public class Consumer {
    public static final String QUEUE_NAME="92faf845-547a-4809-bc9a-eb2820b6344a";
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel= RabbitMqUtils.getChannel();

        DeliverCallback deliverCallback=(consumerTag,message)->{
            System.out.println("收到:"+new String(message.getBody()));
        };
        CancelCallback cancelCallback=(consumerTag)->{
            System.out.println("未收到..");
        };
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
    }
}
