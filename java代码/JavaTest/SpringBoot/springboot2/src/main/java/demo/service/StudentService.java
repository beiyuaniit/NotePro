package demo.service;

import demo.bean.Student;
import demo.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: beiyuan
 * @className: StudentService
 * @date: 2022/8/6  23:03
 */
@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    public Student getStudent(Integer id){
        return studentMapper.getStudent(id);
    }

    public Student getStudent1(Integer id){
        return studentMapper.getStudent1(id);
    }


    public Student selectById(Integer id){
        return studentMapper.selectById(id);
    }
}
