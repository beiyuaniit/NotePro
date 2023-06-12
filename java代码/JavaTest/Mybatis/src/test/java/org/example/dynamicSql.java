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
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author: beiyuan
 * @className: dynamicSql
 * @date: 2022/6/15  22:38
 */
public class dynamicSql {
    SqlSession session=null;
    StusMapper mapper=null;
    List<Student>res;
    @Before
    public void before() throws IOException {
        InputStream in= Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory=new SqlSessionFactoryBuilder().build(in);
        session=factory.openSession();
        mapper=session.getMapper(StusMapper.class);
    }

    @After
    public void after(){
        session.close();
    }

    @Test
    public void selAll(){
        List<Student> list=mapper.selAll();
        for(Student stu:list){
            System.out.println(stu);
        }
    }
    @Test
    public  void selCons(){
        res=mapper.selCons(new Student("ang","abc",21));
        for(Student stu:res){
            System.out.println(stu);
        }
    }

    @Test
    public void updateNor(){
        Student stu=new Student();
        stu.setId(2);
        stu.setAge(21);
        int r=mapper.updateNor(stu);
        session.commit();
    }

    @Test
    public void updateCon(){
        int e=mapper.updateCons(new Student(2,"dufu","bayue",22));
        session.commit();
    }

    @Test
    public void getByIds(){
        Integer[]arr={3,4};
        res=mapper.getByIds(arr);
        for(Student stu:res){
            System.out.println(stu);
        }
    }
}
