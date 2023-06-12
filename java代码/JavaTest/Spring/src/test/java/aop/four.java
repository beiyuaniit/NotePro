package aop;

import aop.fourthVersion.Agent;
import org.junit.Test;

/**
 * @author: beiyuan
 * @className: four
 * @date: 2022/6/29  17:51
 */
public class four {
    @Test
    public void one(){
        aop.fourthVersion.Service bookService=new aop.fourthVersion.BookServiceImpl();
        aop.fourthVersion.Service productService=new aop.fourthVersion.ProductServiceImpl();
        aop.fourthVersion.AOP logAop=new aop.fourthVersion.LogAopImpl();
        aop.fourthVersion.AOP affairAop=new aop.fourthVersion.AffairAopImpl();

        aop.fourthVersion.Agent agent1=new Agent(bookService,logAop);
        agent1.buy();
        System.out.println("---------------------");
        aop.fourthVersion.Agent agent2=new Agent(bookService,affairAop);
        agent2.buy();
        System.out.println("--------------------");
        aop.fourthVersion.Agent agent3=new Agent(productService,logAop);
        agent3.buy();
        System.out.println("-------切入多个切面--------");
        aop.fourthVersion.Agent agent4=new Agent(agent2,logAop);
        agent4.buy();

    }

}
