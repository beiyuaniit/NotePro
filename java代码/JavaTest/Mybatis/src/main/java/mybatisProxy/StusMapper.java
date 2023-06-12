package mybatisProxy;

import org.apache.ibatis.annotations.Param;
import org.example.Student;

import java.util.List;
import java.util.Map;

/**
 * @author: beiyuan
 * @className: StusMapper
 * @date: 2022/6/15  11:10
 */
public interface StusMapper {
    List<Student> getAll();
    List<Student> getByName(
            @Param("conlumnValue") String conlumnValue,
             @Param("conlumnName") String conlumnName
    );
    int insert(Student student);

    List<Student> selAll();

    List<Student> selCons(Student stu);

    int updateNor(Student stu);

    int updateCons(Student stu);

    List<Student> getByIds(Integer []arr);

    Map getMap(Integer id);

    List<Map> getMaps();

    Student getOt(Integer id);
}
