package aop;

import aop.thirdVersion.Agent;
import aop.thirdVersion.BookServiceImpl;
import org.junit.Test;

/**
 * @author: beiyuan
 * @className: three
 * @date: 2022/6/29  16:41
 */
public class three {
    @Test
    public void three(){
        aop.thirdVersion.Service service=new BookServiceImpl();
        aop.thirdVersion.Agent agent=new Agent(service);
        agent.buy();
    }
}
