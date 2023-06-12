package demo;

import demo.bean.Student;
import demo.mapper.StudentMapper;
import demo.service.StudentService;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author: beiyuan
 * @className: demo.TestJdbc
 * @date: 2022/8/2  11:13
 */
//@Slf4j

@SpringBootTest
@DisplayName("Junit5的jdbc测试")
public class TestJdbc {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;

    @AfterAll
    public static  void afterAll(){
        System.out.println("after all..");
    }

    @Test
    public void testLoad(){

       long num= jdbcTemplate.queryForObject("select count(*) from st", Long.class);

        if(jdbcTemplate==null){
            System.out.println("0000000000");
        }

        System.out.println(num);
    }

    @Autowired
    StudentService studentService;

    @Test
    @RepeatedTest(3)
    @DisplayName("测试service层方法")
    public void testService(){
        Student student=studentService.selectById(1);
        System.out.println(student.toString());
    }

}
