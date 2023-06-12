/**
 * @author: beiyuan
 * @className: Student
 * @date: 2022/5/29  22:29
 */
public class Student {
    private String name;
    private int age;

    Student(){

    }
    Student(String name,int age){
        this.name=name;
        this.age=age;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
