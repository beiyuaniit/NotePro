import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class 写入文件时try来自动关闭 {
    public static void main(String[] args) throws FileNotFoundException {
        try(PrintWriter file=new PrintWriter("C:\\Users\\beilinanju\\Desktop\\java代码\\Wecome.txt")){
            file.println("Welcome to javase");
        }
    }
}
