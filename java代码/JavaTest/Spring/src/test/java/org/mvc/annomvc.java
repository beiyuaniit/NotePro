package org.mvc;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: beiyuan
 * @className: annomvc
 * @date: 2022/6/28  22:10
 */
public class annomvc {
    ApplicationContext ac;
    @Before
    public void before(){
        ac=new ClassPathXmlApplicationContext("annomvc.xml");
    }

    @Test
    public void one(){
        org.annomvc.controller.UserController  userController=
                (org.annomvc.controller.UserController) ac.getBean("userController");
        org.annomvc.pojo.User user=new org.annomvc.pojo.User();
        user.setAge(23);
        user.setName("lihe");
        userController.insert(user);
    }
}
