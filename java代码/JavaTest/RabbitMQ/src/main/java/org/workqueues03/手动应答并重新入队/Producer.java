package org.workqueues03.手动应答并重新入队;

import com.rabbitmq.client.Channel;
import org.Utils.RabbitMqUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author: beiyuan
 * @className: Producere
 * @date: 2022/9/29  13:46
 */
public class Producer {
    public final static String QUEUE_NAME="ack_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel= RabbitMqUtils.getChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        Scanner scan=new Scanner(System.in);
        while (scan.hasNext()){
            String message=scan.next();

            channel.basicPublish("",QUEUE_NAME,null,message.getBytes(StandardCharsets.UTF_8));
            System.out.println("发送了消息："+message);
        }
    }
}
