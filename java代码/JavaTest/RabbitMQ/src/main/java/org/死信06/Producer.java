package org.死信06;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import org.Utils.RabbitMqUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author: beiyuan
 * @className: Producer
 * @date: 2022/10/1  22:29
 */
public class Producer {
    public static final String NORMAL_EXCHANGE="normal_exchange";
    public static final String DEAD_EXCHANGE="dead_exchange";
    public static final String NORMAL_QUEUE="normal_queue";
    public static final String DEAD_QUEUE="dead_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        init();
        publish();
    }

    public  static void publish() throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        //设置消息的ttl，过了后成为死信
        //AMQP.BasicProperties properties=new AMQP.BasicProperties().builder().expiration("10000").build();
        for(int i=0;i<10;i++){
            String message=i+"";
            //channel.basicPublish(NORMAL_EXCHANGE,"zhangsan",properties,message.getBytes(StandardCharsets.UTF_8));
            channel.basicPublish(NORMAL_EXCHANGE,"zhangsan",null,message.getBytes(StandardCharsets.UTF_8));
        }
    }

    public static void init() throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE,BuiltinExchangeType.DIRECT);

        //设置死信队列的参数
        Map<String, Object>map=new HashMap<>();
        //map.put("x-message-ttl",100000);//消息过期时间，过了就编程死信
        map.put("x-dead-letter-exchange",DEAD_EXCHANGE);//死信交换机
        map.put("x-dead-letter-routing-key","lisi");//绑定一下死信队列的routingKey
        //map.put("x-max-length",6);//正常队列的最大长度，超出了成为死信
        channel.queueDeclare(DEAD_QUEUE,false,false,false,null);
        channel.queueBind(DEAD_QUEUE,DEAD_EXCHANGE,"lisi");
        channel.queueDeclare(NORMAL_QUEUE,false,false,false,map);
        channel.queueBind(NORMAL_QUEUE,NORMAL_EXCHANGE,"zhangsan");

    }
}
