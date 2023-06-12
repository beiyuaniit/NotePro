package pro_com;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author: beiyuan
 * @date: 2023/2/28  19:10
 */
public class Atomic {

    AtomicInteger balance=new AtomicInteger(0);
    @Test
    public void test01() throws InterruptedException {
        Thread t1=new Thread(()->{
            for(int i=0;i<100002;i++){
                incrementBalance(balance);
            }
        },"t1");

        Thread t2=new Thread(()->{
            for (int i=0;i<100000;i++){
                decrementBalance(balance);
            }
        },"t2");
        t1.start();
        t2.start();
        t2.join();
        t1.join();

        System.out.println(balance);
    }


    private void incrementBalance(AtomicInteger bal){
        while (true){
            int old=bal.get();
            int newVal=old+3;
            if(bal.compareAndSet(old,newVal)){
                break;
            }
        }
    }
    private void decrementBalance(AtomicInteger bal){
        while (true){
            int old=bal.get();
            int newVal=old-3;
            if(bal.compareAndSet(old,newVal)){
                break;
            }
        }
    }

    AtomicReference<String>str=new AtomicReference<>("A");
    @Test
    public void testABA() throws InterruptedException {
        String oldVal=str.get();
        System.out.println("oldVal: "+oldVal);
        ABA();
        Thread.sleep(1000);

        String middleVal=str.get();
        System.out.println("After change middle: "+middleVal);
        str.compareAndSet(middleVal,"C");
        System.out.println("End Value: "+str.get());

        /*
        oldVal: A
        change A->B: B
        change B->A: A
        After change middle: A
        End Value: C
         */
    }
    private void ABA() throws InterruptedException {
        new Thread(()->{
            str.compareAndSet("A","B");
            System.out.println("change A->B: "+str.get());
        }).start();
        Thread.sleep(300);
        new Thread(()->{
            str.compareAndSet("B","A");
            System.out.println("change B->A: "+str.get());
        }).start();
    }


   AtomicStampedReference<String>str1=new AtomicStampedReference<>("A",0);
    @Test
    public void testABAOK() throws InterruptedException {
        int initStamp=str1.getStamp();
        String oldVal=str1.getReference();

        System.out.println("oldVal: "+oldVal);
        ABAOK();
        Thread.sleep(1000);

        String middleVal=str.get();
        System.out.println("After change middle: "+middleVal);
        //初始值和stamp相同则修改，并stamp+1，没有成功则值和stamp都不改变，并返回false
        str1.compareAndSet(middleVal,"C",initStamp,initStamp+1);
        System.out.println("End Value: "+str1.getReference()+" stamp "+str1.getStamp());

        /*
        oldVal: A
        change A->B: B
        change B->A: A
        After change middle: A
        End Value: C
         */
    }
    private void ABAOK() throws InterruptedException {
        new Thread(()->{
            int stamp=str1.getStamp();
            str1.compareAndSet("A","B",stamp,stamp+1);
            System.out.println("change A->B: "+str1.getReference()+" stamp: "+str1.getStamp());
        }).start();
        Thread.sleep(300);
        new Thread(()->{
            int stamp=str1.getStamp();
            str1.compareAndSet("B","A",stamp,stamp+1);
            System.out.println("change B->A: "+str1.getReference()+" stamp: "+str1.getStamp());
        }).start();
    }
}
