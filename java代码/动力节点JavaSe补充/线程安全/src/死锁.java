public class 死锁 {
    public static void main(String[] args) {
        /**
         * 互相锁住
         * 有可能正常执行（其中一个全部执行完后释放了2把锁）
         * 如果锁住第一个后再睡一下就一定是死锁
         */
        Object o1=new Object();
        Object o2=new Object();

        Thread th1=new Thread(new A(o1,o2));
        Thread th2=new Thread(new B(o1,o2));

        th1.start();
        th2.start();
    }
}

class A extends Thread{
    Object o1;
    Object o2;

    public A(Object o1, Object o2) {
        this.o1 = o1;
        this.o2 = o2;
    }

    public void run() {
       synchronized (o1){
           try {
               Thread.sleep(1000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           synchronized (o2){

           }
       }
    }
}

class B extends Thread{
    Object o1;
    Object o2;

    public B(Object o1, Object o2) {
        this.o1 = o1;
        this.o2 = o2;
    }

    public void run() {
        synchronized (o1){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (o2){

            }
        }
    }
}