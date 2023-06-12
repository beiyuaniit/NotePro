package pro_com;

import org.junit.Test;

import java.util.concurrent.locks.LockSupport;

/**
 * @author: beiyuan
 * @date: 2023/2/27  16:27
 */
public class TestLockSupport {

    @Test
    public void test01() throws InterruptedException {
        Thread t1=new Thread(()->{
            System.out.println("开始阻塞...");
            LockSupport.park();
            System.out.println("阻塞结束...");
        },"t2");

        t1.start();
        //t1.join();  调用这个就死锁了
        Thread.sleep(1000);
        System.out.println("开始唤醒...");
        LockSupport.unpark(t1);
        System.out.println("唤醒结束");
    }
}
