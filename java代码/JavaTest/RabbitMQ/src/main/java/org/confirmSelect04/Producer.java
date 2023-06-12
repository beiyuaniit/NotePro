package org.confirmSelect04;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import org.Utils.RabbitMqUtils;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeoutException;

/**
 * @author: beiyuan
 * @className: Producer
 * @date: 2022/9/28  18:44
 */
public class Producer {

    public static void main(String[] args) throws InterruptedException, TimeoutException, IOException {
        //publishMessageIndividually();//1025ms
        //publishMessageBatch();//81 ms
        publishMessageAsync();//51 ms
    }

    public static void publishMessageIndividually() throws IOException, TimeoutException, InterruptedException {
        String queueName= UUID.randomUUID().toString();
        Channel channel= RabbitMqUtils.getChannel();
        channel.queueDeclare(queueName,false,false,false,null);
        channel.confirmSelect();//开启发布确认
        long start=System.currentTimeMillis();

        for(int i=0;i<1000;i++){
            String message=i+"";
            channel.basicPublish("",queueName,null,message.getBytes());
            boolean success=channel.waitForConfirms();
            if(success){
                System.out.println("发送成功");
            }
        }

        long end=System.currentTimeMillis();

        System.out.println("单次确认用时："+(end-start)+" ms" );


    }

    public static void publishMessageBatch() throws IOException, TimeoutException, InterruptedException {
        Channel channel=RabbitMqUtils.getChannel();
        String queueName=UUID.randomUUID().toString();
        channel.queueDeclare(queueName,false,false,false,null);
        channel.confirmSelect();

        long start=System.currentTimeMillis();
        for(int i=0;i<1001;i++){
            String message=i+"";
            channel.basicPublish("",queueName,null,message.getBytes());
            if(i%100==0){
                boolean flag=channel.waitForConfirms();
                if(flag){
                    System.out.println("批量确认成功");
                }
            }
        }
        long end=System.currentTimeMillis();
        System.out.println("批量确认用时："+(end-start)+" ms");
    }

    public static void publishMessageAsync() throws IOException, TimeoutException {
        Channel channel=RabbitMqUtils.getChannel();
        String queueName=UUID.randomUUID().toString();
        channel.queueDeclare(queueName,false,false,false,null);
        channel.confirmSelect();

        //线程安全集合，记录序号和对应消息
        ConcurrentSkipListMap<Long,String>map=new ConcurrentSkipListMap<>();

        long start=System.currentTimeMillis();
        //确认成功回调
        ConfirmCallback ackCallback=(deliverTag,multiple)->{
            System.out.println("确认消息："+deliverTag);
            //2.删除已确认的消息
            if(multiple){
                ConcurrentNavigableMap<Long,String>confirmed=map.headMap(deliverTag);
                confirmed.clear();
            }else {
                map.remove(deliverTag);
            }

        };
        //确认失败回调
        ConfirmCallback nackCallback=(deliverTag,multiple)->{
            System.out.println("确认失败："+deliverTag);
            //3.处理未确认的消息
            String message=map.get(deliverTag);
            System.out.println("未确认的消息："+message);
        };

        channel.addConfirmListener(ackCallback,nackCallback);
        for(int i=0;i<1001;i++){
            String message=i+"";
            channel.basicPublish("",queueName,null,message.getBytes());
            //1.记录已发的消息
            map.put(channel.getNextPublishSeqNo(),message);
        }

        long end=System.currentTimeMillis();
        System.out.println("异步确认用时："+(end-start)+" ms");
    }
}
