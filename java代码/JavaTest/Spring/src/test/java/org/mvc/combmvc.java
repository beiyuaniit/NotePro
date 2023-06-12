package org.mvc;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: beiyuan
 * @className: combmvc
 * @date: 2022/6/28  23:40
 */
public class combmvc {
    ApplicationContext ac;
    @Before
    public  void before(){
        ac=new ClassPathXmlApplicationContext ("combmvc/applicationContext.xml");
    }

    @Test
    public void ont(){
        org.comb.controller.UserController userController=
                (org.comb.controller.UserController)ac.getBean("Controller");
        org.comb.pojo.User user=new org.comb.pojo.User();
        user.setAge(21);
        user.setName("wangbo");
        userController.insert(user);
    }
}
