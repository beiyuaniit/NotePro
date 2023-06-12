package org.八;

import org.junit.Test;


import javax.crypto.spec.PSource;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: beiyuan
 * @className: StreamTest
 * @date: 2022/8/14  11:52
 */
public class StreamTest {

    @Test
    public void test01(){
        //Collection
        List<Integer> list= Arrays.asList(3,2,1,4);
        list.parallelStream().forEach(x-> System.out.println(x));

        System.out.println("-------------------");
        //Arrays
        int[]arr=new int[]{4,3,5,1};
        System.out.println(Arrays.stream(arr).count());

        System.out.println("-------------");
        //Stream的静态方法
        Stream.of(3,2,1).forEach(System.out::println);

        //无限流
        System.out.println("---------------");
        Stream.iterate(0,x->x+3).limit(4).filter(x->x<10).forEach(System.out::println);
    }

    @Test
    public void test02(){
        String []strs1=new String[]{"libai","wangwei","lishangyin","liuzongyuan"};
        Stream.of(strs1).filter(x->x.length()>5).forEach(System.out::println);

        String []strs2=new String[]{"sun","sun","wind","fire"};
        Arrays.stream(strs2).distinct().forEach(System.out::println);

        Integer []nums1={4,5,6};//好像得用Integer，用int不太行。
        Stream.of(nums1).map(x->x+3).forEach(System.out::println);

    }

    @Test
    public void test03(){
        List<String> list=Arrays.asList("sun","moon");

        //得到一个二维得流
        Stream<Stream<Character>>stream1=list.stream().map(StreamTest::formStrToStream);
        stream1.forEach(s->{
            s.forEach(System.out::println);
        });

        //将每个元素取出合并为一个一维得流
       Stream<Character> stream2=list.stream().flatMap(StreamTest::formStrToStream);
       stream2.forEach(System.out::println);

    }

    private static Stream<Character> formStrToStream(String str){
        ArrayList<Character>list=new ArrayList<>();
        for(Character c:str.toCharArray()){
            list.add(c);
        }
        return list.stream();
    }

    @Test
    public void test04(){
        List<Integer> list=Arrays.asList(4,1,2,3);
//        list.stream().sorted().forEach(System.out::println);
//        System.out.println(list.stream().allMatch(x -> x < 5));
//        int i=list.stream().findFirst().get();
//        System.out.println(i);
////        System.out.println(list.stream().count());
//        System.out.println(list.stream().max(Integer::compare));
        //System.out.println(list.stream().reduce(0, Integer::sum));
        //System.out.println(list.stream().reduce((x, y) -> x + y));
        List<Integer> list1 =list.stream().collect(Collectors.toList());
        System.out.println(list1);
    }


}

