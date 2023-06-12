package org.example;

/**
 * @author: beiyuan
 * @className: Student
 * @date: 2022/6/13  23:46
 */
public class Student {
    private Integer id;
    private String name;
    private String email;
    private Integer age;

    public Student(int id, String name, String email, int age) {
        this.id = id;
        this.name = name;
        this.email= email;
        this.age = age;
    }

    public Student( String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public Student() {
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email= email;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }
}
