package org.死信06;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.Utils.RabbitMqUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: beiyuan
 * @className: Consumer01
 * @date: 2022/10/1  22:39
 */
public class Consumer01 {
    //接收正常队列的消息
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        DeliverCallback callback=(consumerTag,message)->{
            String msg=new String(message.getBody());
            if("5".equals(msg)){
                System.out.println("拒绝了："+msg);
                //false表示不放回正常队列。
                channel.basicReject(message.getEnvelope().getDeliveryTag(),false);
            }
            System.out.println("收到了正常的消息："+msg);
        };
        //channel.basicConsume(Producer.NORMAL_QUEUE,true,callback,(consumTag)->{});
        //关闭自动应答，不然拒绝不了消息
        channel.basicConsume(Producer.NORMAL_QUEUE,false,callback,(consumTag)->{});
    }
}
