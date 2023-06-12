package thread01;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author: beiyuan
 * @date: 2023/2/22  21:32
 */
public class test01 {

    @Test
    public void test01() throws Exception {

        System.out.println(Thread.currentThread().getName());
        //创建线程的三种方式
        new Thread(()->{

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("继承Thread类");
            Thread.currentThread().setPriority(3);
            System.out.println(Thread.currentThread().getName());
            System.out.println(Thread.currentThread().getPriority());
        }).start();

        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                Thread.yield();
                System.out.println("继承Runnable接口");
            }
        };
        new Thread(runnable).start();

        FutureTask<Integer>future=new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("实现Callable接口能拿到返回值");
                return 2;
            }
        });
        new Thread(future).start();
        System.out.println(future.get());

        TimeUnit.SECONDS.sleep(2);
    }

    @Test
    public void testJoin() throws InterruptedException {
        Runnable runnable=()->{
            System.out.println(Thread.currentThread().getName());

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        };

       Thread t1=new Thread(runnable);
       Thread t2=new Thread(runnable);
       t1.start();
       t2.start();

       t1.join();
       t2.join();

        System.out.println("t1,t2线程都运行结束");
    }

    @Test
    public void testDaemon() throws InterruptedException {
        Thread t1=new Thread(()->{

            for(int i=0;i<=7;i++){
                System.out.println(i);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t1.setDaemon(true);
        t1.start();

        new Thread(()->{
            //非守护线程
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        //main线程是非守护线程
        TimeUnit.SECONDS.sleep(5);
        //输出了0,1,2,3,4
    }
}