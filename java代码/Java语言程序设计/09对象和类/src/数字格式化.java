import java.text.DecimalFormat;

public class 数字格式化 {
    public static void main(String[] args) {
        /*
        返回的是字符串,传递进去的不能是字符串，是double
         */
        //逗号隔开，保留2位小数
        DecimalFormat df=new DecimalFormat("###,###.##");
        //保留4位小数，不够补0
        DecimalFormat df1=new DecimalFormat(".0000");

        double a=13254.173;

        System.out.println(df.format(a));//13,254.17
        System.out.println(df1.format(a));//13254.1730



    }
}
