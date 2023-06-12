import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class 渎数据 {
    public static void main(String[] args) throws FileNotFoundException {
        try (Scanner input = new Scanner(new File("C:\\Users\\beilinanju\\Desktop\\java代码\\come.txt"))) {
            //读出一个字符串,遇到空白字符就结束
            System.out.println(input.next());

            //读取一行(当前指针之后的
            System.out.println(input.nextLine());

            //读取一个整型（不是整型则报错
            System.out.println(input.nextInt());

            //是否读取到末尾
            System.out.println(input.hasNext());

            //设置Scanner新的读取标记符
            input.useDelimiter("e");


        }
    }
}
