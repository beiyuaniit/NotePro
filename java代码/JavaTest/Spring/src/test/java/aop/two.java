package aop;

import aop.secondVersion.SubBookServiceImpl;
import org.junit.Test;

/**
 * @author: beiyuan
 * @className: two
 * @date: 2022/6/29  16:28
 */
public class two {
    @Test
    public void ont(){
        aop.secondVersion.BookServiceImpl service=new SubBookServiceImpl();
        service.buy();
    }
}
