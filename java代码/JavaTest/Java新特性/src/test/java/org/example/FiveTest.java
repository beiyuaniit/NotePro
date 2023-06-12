package org.example;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: beiyuan
 * @className: FiveTest
 * @date: 2022/8/14  21:39
 */
public class FiveTest {
    @Test
    public void test01(){
        ArrayList list=new ArrayList();
        list.add(2);
        list.add("abc");
        System.out.println(list);
    }

    @Test
    public void test02(){
        User<String> user=new User<>();
        user.setOrder("today");
        System.out.println(user);
    }

    public <E> List<E> fromArrayToList(E[]arr){
        ArrayList<E>list=new ArrayList<>();
        for(E e:arr){
            list.add(e);
        }
        return list;
    }

    @Test
    public void test03(){
        Integer []arr={3,2,1};
        List<Integer> list=fromArrayToList(arr);
        System.out.println(list);

        User<String>user=new User<>();
        user.show(list);
    }


}
