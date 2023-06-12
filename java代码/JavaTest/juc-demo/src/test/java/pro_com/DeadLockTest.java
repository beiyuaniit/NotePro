package pro_com;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: beiyuan
 * @date: 2023/2/27  16:58
 */
public class DeadLockTest {

    private Object lockA=new Object();
    private Object lockB=new Object();
    @Test
    public void test01() throws InterruptedException {
        Thread t1=new Thread(()->{
            synchronized (lockA){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                synchronized (lockB){
                    System.out.println("t1拿到两把锁了");
                }
            }
        },"t1");

        Thread t2=new Thread(()->{
            synchronized (lockB){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                synchronized (lockA){
                    System.out.println("t2拿到2把锁了");
                }
            }
        },"t2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    ReentrantLock lockC=new ReentrantLock();
    ReentrantLock lockD=new ReentrantLock();
    //响应中断防止死锁
    @Test
    public void test02() throws InterruptedException {
        Thread t1=new Thread(()->{
            try {
                lockC.lockInterruptibly();
                TimeUnit.SECONDS.sleep(1);  //睡眠下为了死锁
                lockD.lockInterruptibly();
            } catch (InterruptedException e) {
                System.out.println("t1异常结束");
                throw new RuntimeException(e);
            }finally {
                lockC.unlock();
                lockD.unlock();
                System.out.println("t1正常结束");
            }
        },"t1");

        Thread t2=new Thread(()->{
            try {
                lockD.lockInterruptibly();
                TimeUnit.SECONDS.sleep(1);
                lockC.lockInterruptibly();
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName()+"异常结束");
                throw new RuntimeException(e);

            }finally {
                lockC.unlock();
                lockD.unlock();
                System.out.println("t2正常结束");
            }
        },"t2");

        t1.start();
        t2.start();


        TimeUnit.SECONDS.sleep(4);
        System.out.println("t1被中断，并放弃锁");
        t1.interrupt();

        t1.join();
        t2.join();

    }


    ReentrantLock lockE=new ReentrantLock();
    //公平锁
    @Test
    public void test03() throws InterruptedException {

        class ThredTest implements Runnable{
            private Integer id;
            public ThredTest(Integer id) {
                this.id=id;
            }

            @Override
            public void run() {

                for (int i=0;i<3;i++){
                    lockE.lock();

                    try {

                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("线程"+id+"："+i);
                    lockE.unlock();
                }
            }
        }

        for (int i=0;i<5;i++){
            new Thread(new ThredTest(i)).start();
        }
        TimeUnit.SECONDS.sleep(4);
    }

    ReentrantLock lock1=new ReentrantLock();
    ReentrantLock lock2=new ReentrantLock();
    //让出lock1来解决死锁
    @Test
    public void test04() throws InterruptedException {
        Thread t1=new Thread(()->{
            try {
                while (!lock1.tryLock()){
                    TimeUnit.MILLISECONDS.sleep(10);
                }
                while (!lock2.tryLock()){
                    lock1.unlock();
                    TimeUnit.MILLISECONDS.sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock2.unlock();
                lock1.unlock();
            }

            System.out.println("t1正常结束");

        },"t1");

        Thread t2=new Thread(()->{
            try {
                while (!lock2.tryLock()){
                    TimeUnit.MILLISECONDS.sleep(100);
                }
                while (!lock1.tryLock()){
                    lock2.unlock();
                    TimeUnit.MILLISECONDS.sleep(10);
                }
            }catch (InterruptedException e) {
                throw new RuntimeException(e);
            }finally {
                lock1.unlock();
                lock2.unlock();
            }

            System.out.println("t2正常结束");

        },"t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    //Condition
    ReentrantLock condLock=new ReentrantLock();
    Condition condition=condLock.newCondition();
    @Test
    public void testCondition() throws InterruptedException {
        condLock.lock();
        new Thread(()->{
            condLock.lock();

            try {
                condition.signal();
                System.out.println("t1通知");
            }finally {
                condLock.unlock();
            }

        },"t1").start();
      try {
          System.out.println("等待t1通知");
          condition.await();
      }finally {
          condLock.unlock();
      }
        System.out.println("main收到通知");
    }
}
