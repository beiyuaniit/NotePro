package aop;

import org.junit.Test;

/**
 * @author: beiyuan
 * @className: five
 * @date: 2022/6/29  20:27
 */
public class five {
    @Test
    public void one(){
        aop.fifthVersion.Service bookService=new aop.fifthVersion.BookServiceImpl();
        aop.fifthVersion.AOP affairAop=new aop.fifthVersion.AffairAopImpl();
        aop.fifthVersion.AOP logAop=new aop.fifthVersion.LogAopImpl();

        aop.fifthVersion.Service agent=(aop.fifthVersion.Service)aop.fifthVersion.ProxyFactory.getAgent(bookService,affairAop);
        agent.buy();

        aop.fifthVersion.Service agent1=(aop.fifthVersion.Service)aop.fifthVersion.ProxyFactory.getAgent(agent,logAop);
        agent1.buy();
    }
}
