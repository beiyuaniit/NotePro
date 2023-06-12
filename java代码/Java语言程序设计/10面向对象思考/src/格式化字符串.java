public class 格式化字符串 {
    public static void main(String[] args) {
         /*
         format()静态方法：返回格式化后的字符串
         和printf()相似：显示 格式化的字符串
          */

        //可以用来创建字符类
        String s=String.format("%4d%c%s",1,'w',"353");
        System.out.println(s);
    }
}
