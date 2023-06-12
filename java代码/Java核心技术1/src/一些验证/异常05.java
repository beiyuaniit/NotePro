package 一些验证;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author: beiyuan
 * @className: 异常05
 * @date: 2022/3/12  21:17
 */
public class 异常05 {
    public static void main(String[] args) throws IOException {
        try(var in=new Scanner(new File("src/一些验证/hello.txt"),
                StandardCharsets.UTF_8)){
            while (in.hasNext()){
                System.out.println(in.next());
            }
        }
    }
}
