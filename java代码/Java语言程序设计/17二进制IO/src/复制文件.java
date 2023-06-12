import java.io.*;

public class 复制文件 {
    public static void main(String[] args) throws IOException {
        try(BufferedInputStream input=new BufferedInputStream(new FileInputStream("C:\\" +
                "Users\\beilinanju\\Desktop\\java代码\\Welcome.txt"));
            BufferedOutputStream output=new BufferedOutputStream(new FileOutputStream("C:" +
                    "\\Users\\beilinanju\\Desktop\\Welcome.txt"))){
            /*
            read()返回的是int类型,要个中间值a过度，不然出错
            虽然写入可以用int，但是用byte也OK，毕竟本身就是字节byte
             */
            int a;
            while ((a=input.read())!=-1)
                output.write((byte) a);

        }
    }
}
