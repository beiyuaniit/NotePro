package org.example;

import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: beiyuan
 * @className: NineTest
 * @date: 2022/8/14  18:18
 */
public class NineTest {
    @Test
    public void test01(){
        List<Integer> list=List.of(3,2,1);
        list.remove(3);
        Set<String> set=Set.of("a","Klee");
        Map<Integer,String> map=Map.of(21,"Keqing",32,"Ganyu");
        //这样也行
        Map<String, Integer> map1=Map.ofEntries(Map.entry("Anther",1),Map.entry("Albedo",2));
    }
}
