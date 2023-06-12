package pro_com;

import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: beiyuan
 * @date: 2023/3/1  16:30
 */
public class ThreadPoolTest {

    AtomicInteger taskId=new AtomicInteger(0);
    AtomicInteger threadId=new AtomicInteger(0);
    @Test
    public void test01() throws InterruptedException {
        BlockingQueue<Runnable> blockingQueue=new ArrayBlockingQueue<>(10);
        ThreadFactory threadFactory=new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                //每个工作线程都会通过这里创建
                Thread thread=new Thread(r,"working thread_"+threadId.getAndIncrement());//设置线程名
                return thread;
            }
        };
        ThreadPoolExecutor executor=new ThreadPoolExecutor(5,7,10, 
                TimeUnit.SECONDS,blockingQueue,threadFactory,new ThreadPoolExecutor.DiscardPolicy());

        for(int i=0;i<20;i++){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    /*
                    只会创建5个工作线程，所以这里是0,3,2,1,4  0,3,2,1,4 等按照初始顺序运行。初始顺序每次都随机
                     */
                    //System.out.println(Thread.currentThread().getName());

                    /*
                        所有任务都运行到了，所以是0,2,1,4...20按照每次处理的随机顺序运行
                     */
                    System.out.println(taskId.getAndIncrement());
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });

        }
        TimeUnit.SECONDS.sleep(30);
    }

    @Test
    public void test02() throws InterruptedException {
        ExecutorService executorService=Executors.newFixedThreadPool(2);
        for(int i=0;i<6;i++){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                    System.out.println(taskId.getAndIncrement());
                }
            });
        }
        TimeUnit.SECONDS.sleep(1);

    }

    @Test
    public void test03(){
        Executor executor=Executors.newCachedThreadPool();
        for (int i=0;i<40;i++){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                }
            });
        }
    }

    @Test
    public void test04(){
        Executor executor=Executors.newSingleThreadExecutor();
        for(int i=0;i<20;i++){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                }
            });
        }

    }
}
