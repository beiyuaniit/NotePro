package thread01;

import org.junit.Test;

/**
 * @author: beiyuan
 * @date: 2023/2/25  10:51
 */
public class testSynchronized {
    private final static Object lock=new Object();

    static int count=0;
    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(()->{
            for(int i=0;i<10003;i++){
                synchronized (lock){
                    count++;
                }
            }
        },"t1");
        Thread t2=new Thread(()->{
            for(int i=0;i<10000;i++){
                synchronized (lock){
                    count--;
                }
            }
        },"t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(count); //3
    }


    @Test
    public synchronized void testSynchronized01(){
        System.out.println("等价于synchronized(this)");
    }


    public static synchronized void testSynchronized02(){
        System.out.println("相当于synchronized(Demo.class");
    }
}
