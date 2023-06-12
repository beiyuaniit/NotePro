public class 格式化控制台输出 {
    public static void main(String[] args) {
        //boolean
        System.out.printf("%b"+"\n",true);

        //十进制整数（宽度至少为5,前面补空格
        System.out.printf("%5d"+'\n',4);

        //浮点数（宽度为10,2位小数
        System.out.printf("%10.2f"+'\n',3.14);

        //字符(前面加4空格
        System.out.printf("%5c"+'\n','v');

        //字符串（宽度为20
        System.out.printf("%20s"+'\n',"Welcome to java");


        //

    }
}
