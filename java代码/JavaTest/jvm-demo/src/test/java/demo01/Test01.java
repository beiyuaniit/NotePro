package demo01;


import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author: beiyuan
 * @date: 2023/3/1  22:51
 */
public class Test01 {

    @Test
    public void test01() throws InterruptedException {
        while (true){
            TimeUnit.SECONDS.sleep(1);
        }
    }

    @Test
    public void test02() throws InterruptedException {
        String a=new String("a");
        String b="abc";
    }
}
