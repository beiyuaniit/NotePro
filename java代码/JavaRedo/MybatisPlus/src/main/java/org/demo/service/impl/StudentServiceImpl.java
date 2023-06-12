package org.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.demo.entity.Student;
import org.demo.mapper.StudentMapper;
import org.demo.service.StudentService;
import org.springframework.stereotype.Service;

/**
 * @author: beiyuan
 * @date: 2022/11/1  19:55
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
}
