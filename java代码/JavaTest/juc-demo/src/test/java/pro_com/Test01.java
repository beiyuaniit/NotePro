package pro_com;

import org.junit.Test;
import sun.misc.Unsafe;

/**
 * @author: beiyuan
 * @date: 2023/2/26  22:00
 */
public class Test01 {

    private Object lock=new Object();
    @Test
    public void test01() throws InterruptedException {
        Thread t1=new Thread(()->{
            synchronized (lock){
                System.out.println("t1拿到锁");
                //保证有有线程在等待锁
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("将要释放锁");
                try {
                    lock.wait(3000);
                    System.out.println("hi");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                //拿到锁后再wait释放，等待锁，3秒后不再等待
                System.out.println("wait(3000)在等待3秒后若没有拿到锁，则一直阻塞在这条语句，重新竞争锁");
                System.out.println("那感觉这方法没啥用...不如直接wait()");
            }
        },"t1");

        Thread t2=new Thread(()->{
            //保证后拿锁
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


            synchronized (lock){
                System.out.println("t2拿到锁了");
                try {
                    Thread.sleep(1000*10);//占用锁
                } catch (InterruptedException e) {

                }
                System.out.println("t2将要释放锁");
            }

        },"t2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        /*
        t1拿到锁
        将要释放锁
        t2拿到锁了
        t2将要释放锁
        hi
        wait(3000)在等待3秒后若没有拿到锁，则一直阻塞在这条语句，重新竞争锁
        那感觉这方法没啥用...不如直接wait()
         */

        Unsafe unsafe=Unsafe.getUnsafe();
    }
}
