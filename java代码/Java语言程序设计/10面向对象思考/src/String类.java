public class String类 {
    public static void main(String[] args) {
        //创建字符串类
        String a="jdfns";
        String b=new String(a);
        String c=new String("java");

        //可以更改引用，但是字符串常量池中还保留了备份
        a="dffgjg";
        System.out.println(a);

        //完全相同的字符串在常量池中只有一份数据
    }
}
