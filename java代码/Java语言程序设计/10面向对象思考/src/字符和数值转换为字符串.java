public class 字符和数值转换为字符串 {
    public static void main(String[] args) {
        //用valueOf()
        char a='a';
        int b=24332;
        double c=2.12;
        boolean d=true;

        //属于String类的静态方法
        String s=String.valueOf(d);

        System.out.println(s);

    }
}
