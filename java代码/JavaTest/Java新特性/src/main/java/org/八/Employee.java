package org.八;

/**
 * @author: beiyuan
 * @className: Employee
 * @date: 2022/8/11  14:18
 */
public class Employee {
    private Integer id;
    private String name;

    public Employee() {
    }

    public Employee(Integer id) {
        this.id = id;
    }

    public Employee(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public void doSome(){
        System.out.println("doSome....");
    }
}
