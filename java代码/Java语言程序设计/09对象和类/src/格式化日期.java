import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class 格式化日期 {
    public static void main(String[] args) throws ParseException {
        Date date=new Date();

        //专门负责格式化(年 月 日 日 分 秒 毫秒
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        System.out.println(sdf.format(date));


        //可以用来获取当前的时间
        SimpleDateFormat sdf1=new SimpleDateFormat("yy");
        System.out.println(sdf1.format(date));

        //反过来用字符串创建Date对象
        String str="2021-07-21 12:12:12 333";
        SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        Date date2=sdf2.parse(str);
        System.out.println(date2.toString());
    }
}
