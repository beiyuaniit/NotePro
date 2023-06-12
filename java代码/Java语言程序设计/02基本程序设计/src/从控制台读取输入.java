import java.util.Scanner;

public class 从控制台读取输入 {
    public static void main(String[] args) {
        Scanner input=new Scanner(System.in);

        System.out.println("输入半径：");
        double radius=input.nextDouble();
        System.out.println(radius*radius*3.1415);
    }
}
