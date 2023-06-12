public class 实现线程的方法02 {
    /**
     * 编写一个类，实现java.lang.Runnable接口，实现run方法
     *一般来说，实现接口更加简洁
     */
    public static void main(String[] args) {
        //创造可运行对象
        Mythread Mt=new Mythread();

        //封装成线程
        Thread t=new Thread(Mt);

        //启动线程
        t.start();

    }
}

class Mythread implements Runnable{

    @Override
    public void run() {
        System.out.println("Welcome");
    }
}