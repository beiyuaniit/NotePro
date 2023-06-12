import java.util.Date;
public class Date类 {
    public static void main(String[] args) {
        //默认1970年1月1日至今的时间
        Date date=new Date();

        //1970年1月1日过了毫秒数创建对象
        Date date1=new Date(1);

        //毫秒
        System.out.println(date.getTime());

        //格式化后时间
        System.out.print(date.toString());
    }
}
