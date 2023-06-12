public class 包装类 {
    public static void main(String[] args) {
        /*
        int->Integer
        double->Double
        boolean->Boolean
        long->Long
        char->Character
        float->Float
        byte->Byte
        short->Short
        */

        //无无参构造，所以创建后不可改变
        Integer a=new Integer(3);
        Integer b=new Integer("3");

        //转换为基本数据类型
        int c=a.intValue();

        //比较大小
        System.out.println(a.compareTo(3));

        //字符串转化为数值类型（结果都是十进制
        Integer.parseInt("4");

        //2进制的“11”转化为十进制的“3”
        Integer.parseInt("11",2);

    }
}
