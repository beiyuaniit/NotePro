package demo;

import org.junit.Assert;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: beiyuan
 * @className: TestAssert
 * @date: 2022/8/8  17:51
 */
@DisplayName("断言测试")
@SpringBootTest
public class TestAssert {

    @DisplayName("简单类型")
    @Test
    public void testBase(){
        Assert.assertEquals(3,cal(1,2));
        System.out.println("jiandan...");
    }

    int cal(int i,int j){
        return i+j;
    }

    @DisplayName("assumptions")
    @Test
    public void testAssumptions(){
        Assumptions.assumeTrue(false,"the result is not true");
        System.out.println("ok");
    }


    @ParameterizedTest()
    @ValueSource(strings = {"ten","hundred","thousand"})
    @DisplayName("param")
    public void paramterTest1(String str){
        System.out.println(str);
    }
}
