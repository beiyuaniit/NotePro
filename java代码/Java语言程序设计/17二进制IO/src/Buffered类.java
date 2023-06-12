import java.io.*;

public class Buffered类 {
    public static void main(String[] args)throws IOException {
        try(BufferedOutputStream output=new BufferedOutputStream(new FileOutputStream("C:\\Users\\" +
                "beilinanju\\Desktop\\java代码\\Buffered.dat"))){
          for(int i=0;i<10;i++)
              output.write(i);
        }

        try(BufferedInputStream input=new BufferedInputStream(new FileInputStream("C:\\User" +
                "s\\beilinanju\\Desktop\\java代码\\Buffered.dat"))){
            int a;
            while ((a=input.read())!=-1)
                System.out.println(a);
        }
    }
}
