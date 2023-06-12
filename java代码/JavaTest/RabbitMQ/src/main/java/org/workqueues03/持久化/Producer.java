package org.workqueues03.持久化;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import org.Utils.RabbitMqUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: beiyuan
 * @className: Producer
 * @date: 2022/9/29  14:29
 */
public class Producer {
    public static final String QUEUE_NAME="durable_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel= RabbitMqUtils.getChannel();
        boolean durable=true;//队列持久化
        channel.queueDeclare(QUEUE_NAME,durable,false,false,null);

        String message="队列和消息都要持久化";
        //添加MessageProperties.PERSISTENT_TEXT_PLAIN，表示消息要持久化
        channel.basicPublish("",QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
    }
}
