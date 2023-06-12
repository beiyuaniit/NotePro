import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class 守护线程定时器 {
    /**
     * 用处：
     * 如每隔一段时间进行数据备份
     *
     */

    /**
     * 实现方法
     * 01
     *      sleep()
     *      最原始比较low
     *
     * 02
     *      已经写好的定时器java.util.Timer
     *
     * 03
     *      Spring框架中的SpringTask
     *      主流
     */
    public static void main(String[] args) throws Exception{
        Timer timer=new Timer();
        //Timer timer=new Timer(true);也守护线程的方法创建对象

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date firstTime =sdf.parse("2021-07-15 17:27:00");

        //任务，开始时间，时间间隔
        //每一次任务就是一次run
        timer.schedule(new D(),firstTime,1000*10);
    }

}


//继承TimeTask
class D extends TimerTask{

    @Override
    public void run() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=sdf.format(new Date());

        System.out.println(time);
    }
}