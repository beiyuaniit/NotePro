package org.helloworld02;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: beiyuan
 * @className: Consumer
 * @date: 2022/9/26  22:55
 */
public class Consumer {
    //队列名
    public static final String QUEUE_NAME="hello";

    //
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("192.168.10.100");
        factory.setUsername("admin");
        factory.setPassword("123");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //收到消息回调
        DeliverCallback deliverCallback=(consumerTag,message)->{
            System.out.println("成功接收到消息:");
            System.out.println(new String(message.getBody()));
        };

        //未收到消息回调
        CancelCallback cancelCallback=consumerTag->{
            System.out.println("消息被取消了");
        };
        /**
         *	消费者消费消息
         *	1.消费哪个队列
         *	2.消费成功之后是否要自动应答 true 代表自动应答 false 手动应答
         *	3.消费者未成功消费的回调
         */

        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
    }
}
