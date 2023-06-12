public class 字符串和数字间转换 {
    public static void main(String[] args) {
        String str1="21.37";
        double a=21.7;

        //数字转化为字符串
        String str2=a+"";

        //字符串转化为数字
        double b=Double.parseDouble(str1);

    }
}
