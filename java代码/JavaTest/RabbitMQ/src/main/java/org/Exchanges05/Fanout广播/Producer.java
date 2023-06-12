package org.Exchanges05.Fanout广播;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import org.Utils.RabbitMqUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author: beiyuan
 * @className: Producer
 * @date: 2022/9/29  23:40
 */
public class Producer {
    public static final String EXCHANGE_NAME="log";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        //声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        Scanner in=new Scanner(System.in);
        while (in.hasNext()){
            String message=in.next();
            channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes(StandardCharsets.UTF_8));
            System.out.println("发送了:"+message);
        }
    }
}
