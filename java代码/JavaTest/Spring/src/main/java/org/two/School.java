package org.two;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author: beiyuan
 * @className: School
 * @date: 2022/6/28  11:06
 */
@Component("school")//可以自己为对象取名
public class School {
    //用注解注入时可以不用set方法，
    @Value("至尊殿堂")
    private String name;
    @Value("下界")
    private String address;

    @Override
    public String toString() {
        return "School{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public School() {
    }

    public School(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
