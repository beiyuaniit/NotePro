import java.util.Scanner;

public class 判断闰年 {
    public static void main(String[] args) {
        while(true) {
            System.out.println("输入年份：");
            Scanner input = new Scanner(System.in);
            int a = input.nextInt();
            if (a%400==0||(a%4==0&&a%100!=0))
                System.out.println("闰年");
            else
                System.out.println("不是闰年");
        }
    }
}
