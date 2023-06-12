import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class 对列表有用的方法 {
    public static void main(String[] args) {
        //arrays数组,list列表(链表)
        Integer[]array={1,2,3,5,4};

        //Arrays.aslist返回一个列表，可以用来创建一个列表
        ArrayList<Integer> list=new ArrayList<>(Arrays.asList(array));
        //反之，创建一个数组str
        String []str=new String[list.size()];
        list.toArray(str);

        //排序
        java.util.Collections.sort(list);

        //最大最小
        java.util.Collections.max(list);
        java.util.Collections.min(list);

        //打乱顺序
        java.util.Collections.shuffle(list);

    }
}
