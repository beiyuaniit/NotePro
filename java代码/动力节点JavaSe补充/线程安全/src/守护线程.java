public class 守护线程 {
    /**
     * 分为用户线程和守护线程
     */

    /**
     * 后台线程
     * 是一个死循环线程，一直在执行某些事，主线程结束后会自动结束
     * 典型例子垃圾回收线程，
     */
    public static void main(String[] args) {

    }
}

class C extends Thread{
    @Override
    public void run() {
        while(true){
            //...
        }
    }
}
