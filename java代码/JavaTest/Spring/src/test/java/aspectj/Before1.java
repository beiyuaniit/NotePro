package aspectj;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: beiyuan
 * @className: Before1
 * @date: 2022/6/30  22:24
 */
public class Before1 {


    @Test
    public void before2(){
        ApplicationContext ac=new ClassPathXmlApplicationContext("aspectj/applicationContext.xml");
        SomeServiceImpl someService=(SomeServiceImpl) ac.getBean("someServiceImpl");
        //someService.doSome("baijvyi",21);
        //someService.doAfterReturn(2);
        //System.out.println(someService.doAround("abc"));
        //someService.doAfter("wangwei");
        someService.doAll();
    }
}
