import java.util.Collection;

import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

public class 迭代器 {
    /*
    Map也就是有key又有value的集合不能用(其子类如HashSet也不行,可以用父类之一Set
    其他的可以
     */
    public static void main(String[] args) {
        Collection ct= new TreeSet();
        ct.add(1);
        ct.add(3);
        ct.add(2);

        //获取迭代器
        Iterator it= ct.iterator();

        //遍历
        while (it.hasNext()){
            System.out.println(it.next());
        }

    }
}
