package OneToFiveChapter;

/**
 * @author: beiyuan
 * @className: 自动装箱拆箱08
 * @date: 2022/3/7  17:00
 */
public class 自动装箱拆箱08 {
    public static void main(String[] args) {
        Integer a=100;
        Integer b=new Integer(100);

        System.out.println(a==b);
        System.out.println(a.equals(b));

        Integer c=100;
        Integer d=100;
        System.out.println(c.equals(d));//true



    }
}
