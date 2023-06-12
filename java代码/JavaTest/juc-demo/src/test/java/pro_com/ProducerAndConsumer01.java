package pro_com;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

/**
 * @author: beiyuan
 * @date: 2023/2/25  19:10
 */
public class ProducerAndConsumer01 {
    final int SIZE=3;
    //局部变量没有线程安全问题，全局变量才有
    Queue<Integer>queue=new LinkedList<>();

    @Test
    public void testProCon() throws InterruptedException {
        Thread producer=new Thread(()->{
            int i=0;
           while (true){
               synchronized (queue){ //获得锁
                   if(queue.size()==SIZE){
                       try {
                           queue.wait(3); //释放锁，进入阻塞
                       } catch (InterruptedException e) {
                           throw new RuntimeException(e);
                       }
                   }
                   queue.add(i++);
                   queue.notify(); //唤醒等待同一锁的一个线程

                   try {
                       TimeUnit.SECONDS.sleep(1);
                   } catch (InterruptedException e) {
                       throw new RuntimeException(e);
                   }
               }
               //同步代码快结束后真正释放锁
           }
        },"producer");

        Thread consumer=new Thread(()->{
            while (true){
                synchronized (queue){
                    if(queue.isEmpty()){
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println(queue.poll());
                    queue.notify();

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        },"consumer");

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();
    }
}
