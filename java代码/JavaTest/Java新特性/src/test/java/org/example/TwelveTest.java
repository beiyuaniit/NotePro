package org.example;

import org.junit.Test;

/**
 * @author: beiyuan
 * @className: TwelveTest
 * @date: 2022/8/14  18:35
 */
public class TwelveTest {
    @Test
    public void test01(){
        Fruit fruit=Fruit.ORANGE;
        switch (fruit){
            case GRAPE -> System.out.println(2);
            case PEAR -> System.out.println(1);
            default -> System.out.println(0);
        }
    }
}
enum Fruit{
    PEAR, APPLE, GRAPE, MANGO, ORANGE, PAPAYA;
}
