package com;

import com.mapper.TeacherMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.service.TeacherService;

/**
 * @author: beiyuan
 * @date: 2022/11/1  14:45
 */
@SpringBootTest
public class ModelTestApplication {

    @Autowired
    TeacherMapper teacherMapper;

    @Test
    void getAll() throws Exception {
        teacherMapper.selectById(3);
    }
}
