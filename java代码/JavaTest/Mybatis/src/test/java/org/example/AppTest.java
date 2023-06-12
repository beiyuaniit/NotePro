package org.example;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import proxy.ProxyFactory;
import proxy.Service;
import proxy.SuperStarZhou;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */


    @Test
    public void testProxy(){
        Service zhou=new SuperStarZhou();
        ProxyFactory factory=new ProxyFactory(zhou);
        Service agent=(Service)factory.getAgent();
        agent.sing();
        String msg=agent.show();
        //agent.drunk();
        SuperStarZhou z=(SuperStarZhou)factory.getAgent();
        z.drunk();//直接报错
        System.out.println(agent.getClass());
    }



}
