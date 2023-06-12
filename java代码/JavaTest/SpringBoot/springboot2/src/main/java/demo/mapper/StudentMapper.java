package demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import demo.bean.Student;
import org.apache.ibatis.annotations.Select;

/**
 * @author: beiyuan
 * @className: StudentMapper
 * @date: 2022/8/6  22:54
 */
public interface StudentMapper extends BaseMapper<Student> {
    Student getStudent(Integer id);

    @Select("select * from st where id=#{id}")
    Student getStudent1(Integer id);
}
