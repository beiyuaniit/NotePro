import java.util.ArrayList;

public class ArrayList类 {
    /*
    具有泛型类型
    用来动态扩容存储同一类型对象（比类数组的优势
     */


    public static void main(String[] args) {
        //创建类链表
        ArrayList<I> arr=new ArrayList<>();

        I a=new I();
        I b=new I();
        I c=new I();

        //添加
        arr.add(a);
        arr.add(1,b);

        //删除
        arr.remove(1);

        //获取
        I e=arr.get(1);

        //包含、判空、长度、匹配、清空


    }

}

class I{

}