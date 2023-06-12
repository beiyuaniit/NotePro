package pro_com;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author: beiyuan
 * @date: 2023/2/27  23:56
 */
public class ThreadLocalTest {
    ThreadLocal<Integer> num=new ThreadLocal<>();


    @Test
    public void test01() throws InterruptedException {
        Thread t1=new Thread(()->{
            num.set(1);
            System.out.println("t1第一次赋值:"+num.get());
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("t1第二次取值："+num.get());
        },"t1");

        Thread t2=new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            num.set(2);
            System.out.println("t2的值："+num.get());
        },"t2");
        t1.start();
        t2.start();

        t1.join();
        t2.join();
        /*
        t1第一次赋值:1
        t2的值：2
        t1第二次取值：1
         */
    }

    InheritableThreadLocal<Integer> num1=new InheritableThreadLocal<>();
    @Test
    //测试父进程覆盖问题
    public void test02() throws InterruptedException {
        num1.set(3);

        Thread t1=new Thread(()->{
            System.out.println(num1.get());
            num1.set(4);

            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("最后的值："+num1.get());  //4
        },"t1");

        t1.start();
        TimeUnit.SECONDS.sleep(2);
        num1.set(5);
        System.out.println(num1.get());//5
        t1.join();


    }


    //退不出的循环
     volatile Boolean  run=true;
    @Test
    public void test03() throws InterruptedException {
        Thread t1=new Thread(()->{
            while (run){

            }
            System.out.println("t1退出了循环");
        },"t1");

        t1.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("改为run=false");

        run=false;
        TimeUnit.SECONDS.sleep(3);
    }
}
