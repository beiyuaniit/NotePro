package org.example;

import com.mysql.cj.xdevapi.SessionFactory;
import mybatisProxy.StusMapper;
import org.apache.ibatis.builder.SqlSourceBuilder;
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
import java.util.Map;

/**
 * @author: beiyuan
 * @className: End
 * @date: 2022/6/24  8:59
 */
public class End {

    SqlSession session;
    StusMapper mapper;
    @Before
    public void before() throws IOException {
        InputStream in=Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory=new SqlSessionFactoryBuilder().build(in);
        session=factory.openSession();
        mapper=session.getMapper(StusMapper.class);
    }
    @After
    public void after(){
        session.close();
    }

    @Test
    public void testGetMap(){
        Map map=mapper.getMap(2);
        System.out.println(map);

        List<Map>maps=mapper.getMaps();
        for (Map m:maps){
            System.out.println(m);
        }
    }

    @Test
    public void testGetOt(){
        Student stu=mapper.getOt(1);
        System.out.println(stu);
        System.out.println("==================");
        Student stu1=mapper.getOt(1);
        System.out.println(stu1);
        System.out.println(stu.equals(stu1));
    }
}
