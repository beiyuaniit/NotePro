import java.io.*;

public class Data类 {
    public static void main(String[] args)throws IOException {
        /*
        以基本数据类或字符串写入或读出
        字符串应该用UT8
         */

        //写入数据，不过得传递一个输出对象。。
        try(DataOutputStream output=new DataOutputStream(new FileOutputStream("C:\\" +
                "Users\\beilinanju\\Desktop\\java代码\\Data.dat"))){
           output.writeUTF("java");
           output.writeDouble(3.14);
        }

        //输出
        try(DataInputStream input=new DataInputStream(new FileInputStream("C:\\Users\\" +
                "beilinanju\\Desktop\\java代码\\Data.dat"))){
           System.out.println(input.readUTF()+"　"+input.readDouble());

        }


    }
}
