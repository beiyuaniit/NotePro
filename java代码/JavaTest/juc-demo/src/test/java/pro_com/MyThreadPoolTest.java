package pro_com;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: beiyuan
 * @date: 2023/2/28  22:52
 */
public class MyThreadPoolTest {




    @Test
    public void testThreadPool() throws InterruptedException{
        ThreadPool threadPool=new ThreadPool(3,TimeUnit.SECONDS,2,4);
        for(int i=0;i<10;i++){
            threadPool.execute(new Thread(()->{
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("任务完成");
            }));
        }

        TimeUnit.SECONDS.sleep(100000);
    }

    //
    class ThreadPool{
        /**
         * 阻塞队列，存放未完成的任务
         */
        private BlockingQueue<Runnable> blockingQueue;

        /**
         * 核心线程数
         */
        private int coreSize;

        /**
         * 工作线程
         */
        private Set<Worker> workers=new HashSet<>();

        /**
         * 指定线程最大存活时间
         */
        private TimeUnit timeUnit;
        private long timeout;

        /**
         * 构造函数
         * @param capacity 阻塞队列容量
         */
        ThreadPool(int coreSize,TimeUnit timeUnit,long timeout,int capacity){
            this.coreSize=coreSize;
            this.timeUnit=timeUnit;
            this.timeout=timeout;
            blockingQueue=new BlockingQueue<>(capacity);
        }


        public void execute(Runnable task){
            synchronized (workers){
                if(workers.size()<coreSize){
                    Worker worker=new Worker(task);
                    workers.add(worker);
                    worker.start();
                }else {
                    System.out.println("workers任务已经满，任务放入阻塞队列");
                    blockingQueue.put(task);
                }
            }
        }
        private  class  Worker extends Thread{
            Runnable task;

            public Worker(Runnable task) {
                this.task = task;
                System.out.println("初始化worker..");
            }

            @Override
            public void run() {
                while (task!=null || (task=blockingQueue.take())!=null){
                    try {
                        System.out.println("执行任务");
                        task.run();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        task=null;
                    }
                }
                synchronized (workers){
                    workers.remove(this);
                    System.out.println("移除worker");
                }
            }


        }
    }

    //阻塞，未满时可生产，未空时可消费。且大部分方法要满足多线程并发
    private class BlockingQueue<T> {

        //阻塞队列
        private Deque<T> blockingQueue;
        //容量
        private  int capacity;
        //锁
        private ReentrantLock lock;
        //条件
        private Condition fullQueue;
        private Condition emptyQueue;

        public BlockingQueue(int capacity){
            this.capacity=capacity;
            blockingQueue=new ArrayDeque<>(capacity);
            lock=new ReentrantLock();
            fullQueue=lock.newCondition();
            emptyQueue=lock.newCondition();
        }

        /**
         * 获取第一个任务,相当于pop()
         */
        public T take() {
            lock.lock();
            try{
                while (blockingQueue.isEmpty()){
                    //等待满
                    try {
                        fullQueue.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                T task=blockingQueue.removeFirst();
                emptyQueue.signalAll();
                return task;
            } finally {
                lock.unlock();

            }
        }

        public void put(T task){
            lock.lock();
            try {
                while (blockingQueue.size()==capacity){
                    try {
                        System.out.println("阻塞队列已满,等待有空闲");
                        emptyQueue.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                blockingQueue.addLast(task);
                fullQueue.signalAll();  //唤醒消费者
            }finally {
                lock.unlock();
            }
        }

        public int getSize(){
            lock.lock();
            try{
                return blockingQueue.size();
            }finally {
                lock.unlock();
            }
        }

        public int getCapacity(){
            lock.lock();
            try{
                return this.capacity;
            }finally {
                lock.unlock();
            }
        }

        public T takeNanos(long timeout, TimeUnit unit) {
            // 转换等待时间
            lock.lock();
            try {
                long nanos = unit.toNanos(timeout);
                while (blockingQueue.isEmpty()) {
                    try {
                        // awaitNanos会返回剩下的等待时间
                        //awaitNanos:Causes the current thread to wait until it is signalled or interrupted, or the specified waiting time elapses.
                        nanos = fullQueue.awaitNanos(nanos);
                        if (nanos < 0) {
                            return null;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                T task = blockingQueue.removeFirst();
                emptyQueue.signalAll();
                return task;
            } finally {
                lock.unlock();
            }
        }
    }

}
