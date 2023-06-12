package OneToFiveChapter;

import java.io.Console;

/**
 * @author: beiyuan
 * @className: Console输入密码01
 * @date: 2022/3/1  22:47
 */
public class Console输入密码01 {
    public static void main(String[] args) {
        /*
        cons为null
        原因
　　      如果Java程序要与windows下的cmd或者Linux下的Terminal交互，就可以使用这个Java Console类代劳。
         Java要与Console进行交互，不总是能得到可用的Java Console类的。一个JVM是否有可用的Console，依
         赖于底层平台和JVM如何调用。如果JVM是在交互式命令行（比如Windows的cmd）中启动的，并且输入输出
         没有重定向到另外的地方，那么就可以得到一个可用的Console实例。
        在命令行下运行就没有什么问题
         */
        Console cons= System.console();
        if(cons!=null){
            String name=cons.readLine("User name:");
            char []password=cons.readPassword("Password:");
        }

    }
}
