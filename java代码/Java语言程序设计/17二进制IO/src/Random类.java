import java.io.IOException;
import java.io.RandomAccessFile;

public class Random类 {
    /*
    RandomAccessFile：可读可写
    随机访问文件，允许从文件任何位置进行读写
    若文件不存在，则创建文件

     */
    //只有2种权限：r  读   rw 读写

    public static void main(String[] args) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile("C:\\Us" +
                "ers\\beilinanju\\Desktop\\java代码\\Random.txt", "rw")) {
             //写
            for(int i=0;i<10;i++)
               raf.write(i);

            //设置指针位置（类似于数组下标
            raf.seek(3);

            //读
            System.out.println(raf.read());

            //剩下字节数
            System.out.println(raf.length());

            //byte数组off位置开始，写入len字节到文件
            byte []a=new byte[3];
            a[0]=1;a[1]=2;a[2]=3;
            raf.write(a,0,3);

            //byte数组off位置开始，读取len字节到数组
            byte[]b=new byte[5];
            System.out.println(raf.read(b,0,5));
            System.out.println(b[4]);

        }
    }
}
