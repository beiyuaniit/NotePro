package org.workqueues03.轮询消费;

import com.rabbitmq.client.Channel;
import org.Utils.RabbitMqUtils;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author: beiyuan
 * @className: Producer
 * @date: 2022/9/27  20:14
 */
public class Producer {
    public static final String QUEUE_NAME="hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel= RabbitMqUtils.getChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        Scanner in=new Scanner(System.in);

        while (in.hasNext()){
            String message=in.next();
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            System.out.println("发送了："+message);
        }
    }
}
