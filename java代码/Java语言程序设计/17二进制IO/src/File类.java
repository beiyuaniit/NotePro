import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class File类 {
    public static void main(String[] args) throws IOException {
        /*
        try-with-resourece自动关闭流，try中可以打开多个流，用分号;隔开
         */

        //写入数据,append默认是false
        try(FileOutputStream output=new FileOutputStream("C:\\Users\\beilinanju\\" +
                "Desktop\\java代码\\Welcome.dat",false)){
            for(int i=0;i<10;i++)
                output.write(i);
        }

        //读出数据
        try(FileInputStream input=new FileInputStream("C:\\Users\\beilinanju\\D" +
                "esktop\\java代码\\Welcome.dat")){
           int value;
           //渎到末尾返回-1(不是0
           while ((value=input.read())!=-1){
               System.out.println(value);
           }

        }

    }
}
