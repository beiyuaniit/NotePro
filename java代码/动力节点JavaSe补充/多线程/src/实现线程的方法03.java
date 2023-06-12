import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class 实现线程的方法03 {
    /**
     * 实现Callable接口
     *
     */

    /**
     * 优点；
     * 可以获得线程执行结果
     * 以Object返回
     *
     * 缺点：
     * 在main方法中实现，因为可能要等待 线程结束，导致main方法受阻塞
     *
     */
    public static void main(String[] args) {

        //创建“未来任务类”
        FutureTask task=new FutureTask(new Callable() {
            @Override
            public Object call() throws Exception {
                System.out.println("Welcome");
                return null;
            }
        });

        //创建线程对象
        Thread thread=new Thread(task);

        thread.start();

    }
}
