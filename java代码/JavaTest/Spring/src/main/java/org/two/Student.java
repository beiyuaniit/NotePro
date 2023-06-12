package org.two;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author: beiyuan
 * @className: Student
 * @date: 2022/6/27  0:02
 */
@Component//创建的对象默认驼峰命名：student
public class Student {
    @Value("hezhizhang")
    private String name;
    @Value("21")//类型要匹配
    private int age;
    //@Autowired //按类型注入，在最终得xml文件（此处是two.xml)能拿到的对象找一个同源类型的注入
    @Autowired//不加@Autowired，只用@Qualifier的话注入不成功，为null
    @Qualifier("school")//按对象名注入
    private School school;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", school=" + school +
                '}';
    }



    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
