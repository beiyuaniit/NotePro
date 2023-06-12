import java.math.BigDecimal;
import java.math.BigInteger;

public class 任意大小和精度 {
    public static void main(String[] args) {
        BigInteger a=new BigInteger("4873748388327323");

        BigDecimal b=new BigDecimal(1.0);
        BigDecimal c=new BigDecimal(3);

        //控制小数点后的位数
        //1除以3取20位
        BigDecimal d=b.divide(c,20,BigDecimal.ROUND_UP);

        System.out.println(d);
    }
}
