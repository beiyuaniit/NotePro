import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: beiyuan
 * @className: JavaSE
 * @date: 2022/5/29  22:32
 */
public class JavaSE {
    public static void main(String[] args) {
        //fastjson
        String name="libai";
        int age=321;
        Student stu=new Student(name,age);
        Student stu1=new Student(name,age);
        List<Student> list=new ArrayList<>();
        list.add(stu);
        list.add(stu1);
        String res= JSON.toJSONString(list);
        System.out.println(res);

    }
}
