import java.util.Comparator;
import java.util.TreeMap;

public class 排序树TreeMap和自定义比较器 {
    //自定义比较器在待使用的类内
    static Comparator<C> cmp=new Comparator<C>() {
        @Override
        public int compare(C o1, C o2) {
            return o1.c-o2.c;
        }
    };


    public static void main(String[] args) {
        TreeMap<C,Integer> mes=new TreeMap<C,Integer>(cmp);
    }
}

class C{
    int c;
}