public class StringBuilder类的其他方法 {
    public static void main(String[] args) {
        StringBuilder s=new StringBuilder("Welcome to java");

        //返回串
        System.out.println(s.toString());

        //长度
        System.out.println(s.length());

        //构造器容量
        System.out.println(s.capacity());

        //返回字符
        System.out.println(s.charAt(4));

        //返回子串
        System.out.println(s.substring(0,5));

    }
}

