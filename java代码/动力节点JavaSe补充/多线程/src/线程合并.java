public class 线程合并 {
    /**
     * 当前线程阻塞
     *
     */
    public static void main(String[] args) throws InterruptedException {
        Thread MY=new Thread(new MYthread());

        MY.start();

        //主线程阻塞，MY线程执行，MY执行完再到主线程
        MY.join();
    }
}

class MYThread implements Runnable{

    @Override
    public void run() {

    }
}
