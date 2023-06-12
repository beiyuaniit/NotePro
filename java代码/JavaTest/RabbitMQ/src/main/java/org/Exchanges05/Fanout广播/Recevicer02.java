package org.Exchanges05.Fanout广播;


import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.Utils.RabbitMqUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: beiyuan
 * @className: Recevicer01
 * @date: 2022/9/29  23:21
 */
public class Recevicer02 {
    //交换机名
    public static final String EXCHANGE_NAME="log";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        //声明一个临时队列
        String queueName = channel.queueDeclare().getQueue();
        //绑定交换机
        channel.queueBind(queueName,EXCHANGE_NAME,"");

        System.out.println("R2等待接收消息...");

        DeliverCallback deliverCallback=(consumerTag,message)->{
            System.out.println("收到："+new String(message.getBody()));
        };
        CancelCallback cancelCallback=consumerTag->{
            System.out.println("未收到："+consumerTag);
        };

        channel.basicConsume(queueName,true,deliverCallback,cancelCallback);

    }
}
