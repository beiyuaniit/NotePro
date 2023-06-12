import java.util.Calendar;
import java.util.GregorianCalendar;

public class GregorianCalendar类 {
    public static void main(String[] args) {
        Calendar date=new GregorianCalendar();

        //获取年
        System.out.println(date.get(Calendar.YEAR));

    }
}
