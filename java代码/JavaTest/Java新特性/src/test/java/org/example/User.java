package org.example;

import java.util.List;

/**
 * @author: beiyuan
 * @className: User
 * @date: 2022/8/14  21:44
 */
public class User<T> {

    private String name;
    private Integer age;
    private T order;


    public void show(List<?> list){
        //list.add(2);    不能写，除了null
        list.add(null);
        Object o=list.get(0);//可以读，为Object
        System.out.println(list);
    }

    public  <E> void showing(List<E> list){
        //list.add(3);  一样能写，除了null
        list.add(null);
        Object o=list.get(0);//可以读，为Object
        System.out.println(list);
    }
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", order=" + order +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public T getOrder() {
        return order;
    }

    public void setOrder(T order) {
        this.order = order;
    }
}
