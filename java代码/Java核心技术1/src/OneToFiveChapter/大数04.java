package OneToFiveChapter;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author: beiyuan
 * @className: 大数04
 * @date: 2022/3/3  23:15
 */
public class 大数04 {
    public static void main(String[] args) {
        BigInteger a=BigInteger.valueOf(42);
        BigInteger b=new BigInteger("4279423");
        System.out.println(b.divide(a));
        System.out.println(b.compareTo(a));
        System.out.println(a.compareTo(b));
    }
}
