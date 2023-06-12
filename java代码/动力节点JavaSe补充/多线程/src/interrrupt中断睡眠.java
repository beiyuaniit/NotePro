public class interrrupt中断睡眠 {
    /**
     *  如果有睡眠状态，则终止
     * 不是静态方法，所以可以使线程对象醒来
     * 机理：在try抛出异常
     */
    public static void main(String[] args) {
        Thread tH=new Thread(new MYthread());
        //启动
        tH.start();

        //终止睡眠
        tH.interrupt();


    }
}

class MYthread implements Runnable{

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
