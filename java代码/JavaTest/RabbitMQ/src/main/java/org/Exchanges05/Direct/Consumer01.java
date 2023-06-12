package org.Exchanges05.Direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.Utils.RabbitMqUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: beiyuan
 * @className: Consumer
 * @date: 2022/10/1  10:45
 */
public class Consumer01 {
    public static final String EXCHANGE_NAME="direct_log";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        channel.queueDeclare("console",false,false,false,null);
        //可以绑定多个
        channel.queueBind("console",EXCHANGE_NAME,"info");
        channel.queueBind("console",EXCHANGE_NAME,"warning");

        DeliverCallback deliverCallback=(consumerTag,message)->{
            System.out.println("收到了："+message);
        };

        channel.basicConsume("console",true,deliverCallback,consumerTag->{});
    }
}
