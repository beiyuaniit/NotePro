package org.八;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.*;

/**
 * @author: beiyuan
 * @className: LambdaTest
 * @date: 2022/8/10  23:01
 */
public class LambdaTest {

    @Test
    public void test01(){
        Runnable r1=new Runnable() {
            @Override
            public void run() {
                System.out.println("r1");
            }
        };
        r1.run();//直接调用其方法，就不start()了
        //使用Lambda,返回一个函数式接口的实例
        Runnable r2=()->{
            System.out.println("r2");
        };
        r2.run();
    }

    @Test
    public void test02(){
        Comparator<Integer> com1=new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1,o2);
            }
        };
        System.out.println(com1.compare(2,3));

        Comparator<Integer>com2=(o1,o2)->Integer.compare(o1,o2);
        System.out.println(com2.compare(3,2));

        Comparator<Integer> com3=Integer::compare;
        System.out.println(com3.compare(0,0));
    }

    @Test
    public void test03(){
        MyFunctionalInterface myF=()-> {return "welcome";};
    }

    @Test
    public void test04(){
        happyTime(2.5,money-> System.out.println("it costs "+money+" money"));
    }

    public void happyTime(Double money,Consumer<Double> con){
        con.accept(money);
    }

    @Test
    public void test05(){
        List<String>strs= Arrays.asList("stirng","today","monlight","apples");
        List<String>filterStrs=filterStrings(strs,s -> s.contains("s"));
        System.out.println(filterStrs);
    }

    public List<String> filterStrings(List<String>list, Predicate<String>pre){
        ArrayList<String> filteredList=new ArrayList<>();
        list.forEach(s -> {
           if(pre.test(s)){
               filteredList.add(s);
           }
        });
        return filteredList;
    }

    @Test
    public void test06(){
        /*
        类::实例方法
        Ccomparator中的int compare(T o1, T o2);
        String 中int t1.compareTo(t2)
         */

        Comparator<String> com=String::compareTo;
        System.out.println(com.compare("bac","abc"));
    }

    @Test
    public void test07(){
        Supplier<Employee>sup=Employee::new;
        Employee emp=sup.get();
        emp.doSome();
    }

    @Test
    public void test08(){
        Function<Integer, Employee> fun=Employee::new;
        fun.apply(2).doSome();
    }

    @Test
    public void test09(){
        BiFunction<Integer,String,Employee> bif=Employee::new;
        bif.apply(2,"ok").doSome();
    }

    @Test
    public void test10(){
        Function<Integer,String[]> fun=String[]::new;
        String[] arr=fun.apply(3);
        System.out.println(Arrays.toString(arr));
    }
}
