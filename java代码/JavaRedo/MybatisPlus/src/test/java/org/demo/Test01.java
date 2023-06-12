package org.demo;

import org.demo.entity.Student;
import org.demo.mapper.StudentMapper;
import org.demo.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: beiyuan
 * @className: Test01
 * @date: 2022/8/10  17:07
 */
@SpringBootTest
public class Test01 {

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    StudentService studentService;
    @Test
    public void test1(){
        Student student = studentMapper.selectById(13);
        System.out.println(student);
    }

    @Test
    void test2(){
        studentService.list().forEach(System.out::println);
    }
}
