package org.two;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: beiyuan
 * @className: test
 * @date: 2022/6/28  16:36
 */
public class test {
    ApplicationContext ac;
    @Before
    public void before(){
        ac=new ClassPathXmlApplicationContext("two.xml");
    }

    @Test
    public void one(){
        Student student=(Student)ac.getBean("student");
        System.out.println(student);

        School subsch=(SubSchool)ac.getBean("subSchool");
        System.out.println(subsch.getName());
    }
}
