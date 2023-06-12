package org.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.demo.entity.Student;
import org.springframework.stereotype.Repository;

/**
 * @author: beiyuan
 * @className: StudentMapper
 * @date: 2022/8/10  17:08
 */
@Repository
public interface StudentMapper extends BaseMapper<Student> {
}
