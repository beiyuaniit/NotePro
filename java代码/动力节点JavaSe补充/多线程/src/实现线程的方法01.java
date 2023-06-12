public class 实现线程的方法01 {
    /**
     * 编写一个类，继承java.lang.Thread，重写run()
     * run()相当于分支线程的main方法，该段程序会运行在分支栈中
     */
    public static void main(String[] args) {
        //创建线程对象
        myThread MT=new myThread();

        //启动线程(开启新的栈空间，执行后这段代码就结束了，启动成功的线程会自动调用run
        //直接MT.run();run里面的代码会执行，但是不会开辟新的栈，本质上还是单线程
        MT.start();
    }
}

class myThread extends Thread{
    @Override
    public void run() {
        System.out.println("Welcome");
    }
}
