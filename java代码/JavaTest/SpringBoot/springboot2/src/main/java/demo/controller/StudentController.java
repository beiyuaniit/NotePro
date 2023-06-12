package demo.controller;

import demo.bean.Student;
import demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: beiyuan
 * @className: StudentController
 * @date: 2022/8/6  23:01
 */
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @RequestMapping("/stu")
    public Student getStudent(Integer id){
        return studentService.getStudent(id);
    }

    @RequestMapping("/stu1")
    public Student getStudent1(Integer id){
        return studentService.getStudent1(id);
    }

    @RequestMapping("/stuPlus")
    public Student selectById(Integer id){
        return studentService.selectById(id);
    }
}
