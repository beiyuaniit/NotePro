public class 取整方法 {
    public static void main(String[] args) {
        //返回值都是double

        //向上取整
        double a=Math.ceil(2.1);

        //向下取整
        double b=Math.floor(2.1);

        //就近取整
        double c=Math.rint(2.1);

        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
    }
}
