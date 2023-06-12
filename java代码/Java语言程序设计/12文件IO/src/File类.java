import java.io.File;

public class File类 {
    public static void main(String[] args) {
        //创建一个文件对象（不是创建一个文件。只能是已经存在的文件
        File file=new File("C:\\Users\\beilinanju\\Desktop\\java代码\\" +
                "Welcome.txt");
        /*
        对文件进行操作
         */

        //是否存在
        System.out.println(file.exists());

        //大小（单位字节
        System.out.println(file.length());

        //是否可读读写
        System.out.println(file.canRead());
        System.out.println(file.canWrite());

        //是否是文件和目录
        System.out.println(file.isFile());
        System.out.println(file.isDirectory());

        //获取名字和路径
        System.out.println(file.getName());
        System.out.println(file.getAbsolutePath());

        //最后修改时间
        System.out.println(file.lastModified());


    }
}
