package org.example;

import mybatisProxy.StusMapper;
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
import java.util.UUID;

/**
 * @author: beiyuan
 * @className: ProxyMybatisTest
 * @date: 2022/6/15  11:16
 */
public class ProxyMybatisTest {
    SqlSession session;
    StusMapper mapper;
    @Before
    public void before() throws IOException {
        InputStream in= Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory=new SqlSessionFactoryBuilder().build(in);
        session=factory.openSession();
        //获取动态代理对象
         mapper=session.getMapper(StusMapper.class);
    }

    @After
    public void after(){
        session.close();
    }

    @Test
    public void getAll(){
        //调用接口方法
        List<Student>list=mapper.getAll();
        for(Student stu:list){
            System.out.println(stu);
        }
    }

    @Test
    public void getByName(){
        List<Student> list=mapper.getByName("2","id");
        for(Student stu:list){
            System.out.println(stu);
        }
    }

    @Test
    public void insert(){
        Student stu=new Student("Tartaglia","zhidong",18);
        mapper.insert(stu);
        session.commit();
        System.out.println(stu);
    }

    @Test
    public void uuid(){
        UUID uuid=UUID.randomUUID();
        System.out.println(uuid.toString());
    }
}
