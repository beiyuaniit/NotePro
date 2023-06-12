package org.mvc;

import org.junit.Before;
import org.junit.Test;
import org.mvc.controller.UserController;
import org.mvc.pojo.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: beiyuan
 * @className: xmlmvc
 * @date: 2022/6/28  21:12
 */
public class xmlmvc {
    ApplicationContext ac;
    @Before
    public void before(){
        ac=new ClassPathXmlApplicationContext("beanmvc.xml");
    }

    @Test
    public void ont(){
        org.beanmvc.controller.UserController userController=(org.beanmvc.controller.UserController)ac.getBean("controller");
        org.beanmvc.pojo.User user=new org.beanmvc.pojo.User();
        user.setAge(21);
        user.setName("lisaaai");

        userController.insert(user);
    }
}
