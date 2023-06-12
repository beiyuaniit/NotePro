package pro_com;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: beiyuan
 * @date: 2023/2/27  23:32
 */
public class MyBlockingQueue<E> {

    private ReentrantLock lock=new ReentrantLock();
    private Condition Full=lock.newCondition();
    private Condition Empty=lock.newCondition();

    List<E>queue=new LinkedList<>();//底层实现

    private int size;

    public MyBlockingQueue(int size) {
        this.size = size;
    }


    public E put(E e) throws InterruptedException {
        lock.lock();
        try{
           while (queue.size()==size){
               Empty.await();
               queue.add(e);
               Full.signal();
           }
        }finally {
           lock.unlock();
        }
        return e;
    }

    public E pop() throws InterruptedException {
        E e=null;
        lock.lock();
        try{
            while (queue.size()==0){
                Full.await();
                e=queue.remove(queue.size()-1);
                Empty.signal();
            }
        }finally {
            lock.unlock();
        }
        return e;
    }


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
