import java.util.ArrayList;
import java.util.List;

public class 生产者和消费者模式实现 {
    /**
     * 一开始2个线程都在锁池中寻找锁（都是唤醒状态
     * 01已经得到锁
     *      满足条件
     *          能拿到锁
     *          执行当前任务
     *          唤醒其他线程
     *          等synchronized结束后释放锁
     *
     *      不满足条件
     *          进入等待（不能再拿到锁，防止错误拿锁
     *          释放锁
     *          当前线程结束（不执行之后的
     */


    public static void main(String[] args) {
        List list=new ArrayList();//只创建一次

        Thread t1=new Thread(new Producer(list));
        Thread t2=new Thread(new Consumer(list));

        t1.start();
        t2.start();

    }
}


//生产线程
class Producer extends Thread {
    private List list;

    public Producer(List list) {
        this.list = list;
    }//指向全局变量

    @Override
    public void run() {
        while (true) {
            synchronized (list) {
                if (list.size() > 0) {
                    //已经有元素，进入等待
                    try {
                        list.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                list.add(new Object());
                System.out.println("Add");
                //唤醒消费者
                list.notify();

            }
        }
    }
}

class Consumer extends Thread{
    private List list;

    public Consumer(List list) {
        this.list = list;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (list) {
                //仓库空，消费者等待
                if (list.size() == 0) {
                    try {
                        list.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                list.remove(0);
                System.out.println("remove");
                //唤醒生产者生产
                list.notify();
            }
        }
    }
}
