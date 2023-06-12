package org.example;

import com.mysql.cj.Session;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author: beiyuan
 * @className: MybatisTest
 * @date: 2022/6/14  9:47
 */
public class MybatisTest {

    SqlSession session;
    @Before//每个测试方法执行前都会执行
    public void before() throws IOException {
        //使用输入流读取属性配置文件SqlMapConfig.xml
        InputStream in= Resources.getResourceAsStream("SqlMapConfig.xml");
        //创建SqlSessionFactory工厂
        SqlSessionFactory factory=new SqlSessionFactoryBuilder().build(in);
        //获取SqlSession对象
         session=factory.openSession();
    }

    @After//同理
    public void after(){
        //关闭SqlSession
        session.close();
    }
    @Test
    public void testStu() throws IOException {

        //执行sql

        List<Student> list=session.selectList("stu.getAll");//通过List容器返回
        for(Student stu:list){
            System.out.println(stu);
        }

        Student student1=  session.selectOne("stu.getById",1);//单个
        System.out.println(student1);

        List<Student> list1=session.selectList("stu.getByName","ang");
        System.out.println();
        for(Student stu:list1){
            System.out.println(stu);
        }

    }

    /*
    增删改查要手动提交事务

     */
    @Test
    public void testCRUD() throws IOException {
        int res1=session.insert("stu.insertOne",new Student("libai","jingyesi",21));
       session.commit();
    }


}
