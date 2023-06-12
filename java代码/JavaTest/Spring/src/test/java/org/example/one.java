package org.example;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



/**
 * @author: beiyuan
 * @className: one
 * @date: 2022/6/28  11:35
 */
public class one {
    ApplicationContext ac;
    @Before
    public  void before(){
        ac=new ClassPathXmlApplicationContext("applicationConfig.xml");
    }
    @Test
    public void  stu(){
        //创建容器对象
        //ApplicationContext ac=new ClassPathXmlApplicationContext("applicationConfig.xml");
        //取出的都是Object对象
        Student stu=(Student)ac.getBean("stu");
        System.out.println(stu);
    }

    @Test
    public void sch(){
        School sch1=(School) ac.getBean("sch1");
        System.out.println(sch1);
        School sch2=(School)ac.getBean("sch2");
        System.out.println(sch2);
        School sch4=(School)ac.getBean("sch4");
        System.out.println(sch4);


    }
}
