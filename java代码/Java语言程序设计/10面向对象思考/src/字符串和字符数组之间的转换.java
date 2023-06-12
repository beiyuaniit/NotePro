public class 字符串和字符数组之间的转换 {
    public static void main(String[] args) {
        String s="java";
        char []ch={'a','b','c'};

        //字符串转换为数组
        char []a=s.toCharArray();

        //数组转换为字符串
        String b=new String(ch);
    }
}
