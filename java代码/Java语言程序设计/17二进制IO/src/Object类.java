import java.io.*;

public class Object类 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        /*
        包含了Data类的所有方法，额外可以读/写可序列化的对象
         */

        //用Buffered提高效率
        try(ObjectOutputStream output=new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream("C:\\" +
                        "Users\\beilinanju\\Desktop\\java代码\\Object.dat"))
        )){
            output.writeUTF("java");
            output.writeDouble(3.14);
            output.writeObject(new java.util.Date());
        }

        //读取时确保接收的类型一致
        try(ObjectInputStream input=new ObjectInputStream(new FileInputStream("C:" +
                "\\Users\\beilinanju\\Desktop\\java代码\\Object.dat"))){
            String name=input.readUTF();
            double num=input.readDouble();
            //所以这里要进行类型转换
            java.util.Date date=(java.util.Date)(input.readObject());

            System.out.println(name+"　"+num+" "+date);

        }


    }
}
