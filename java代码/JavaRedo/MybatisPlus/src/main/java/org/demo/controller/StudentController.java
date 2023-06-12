package org.demo.controller;

import org.demo.entity.Student;
import org.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: beiyuan
 * @date: 2022/11/1  20:02
 */
@RestController
@RequestMapping("/stu")
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping("list")
    public List<Student> getStudentList(){
        return studentService.list();
    }
}
