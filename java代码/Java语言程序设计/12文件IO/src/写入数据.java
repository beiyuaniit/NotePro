import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class 写入数据 {
    //要抛异常
    public static void main(String[] args) throws FileNotFoundException {
        /*
        若文件不存在，就创建该文件
         */
        PrintWriter output=new PrintWriter("C:\\Users\\beilinanju\\Desktop\\java代码\\come.txt");
        output.println("Welcome to java");

        //所有输出流都是可刷新的，将管道中的数据全部输出
        output.flush();
        output.close();

    }


}
