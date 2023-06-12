import java.util.Scanner;

public class 从控制台读取字符 {
    public static void main(String[] args) {
        Scanner input=new Scanner(System.in);

        String str=input.next();

        //char  只能这样，因为char不是类
        char ch=str.charAt(0);

        System.out.println(str);
        System.out.println(ch);
    }
}
