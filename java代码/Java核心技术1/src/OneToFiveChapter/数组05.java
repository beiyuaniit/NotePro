package OneToFiveChapter;

import java.util.Arrays;

/**
 * @author: beiyuan
 * @className: 数组05
 * @date: 2022/3/3  23:28
 */
public class 数组05 {
    public static void main(String[] args) {
        //匿名数组
        getOne(new int[]{1,2});
        //new int[]{1,2};//直接创建是错的

        int []a={1,2,3};
        //打印
        System.out.println(Arrays.toString(a));

        int []b=new int[5];
        b=Arrays.copyOf(a,2);
        System.out.println(Arrays.toString(b));
    }

    public static void getOne(int[]a){
        System.out.println(a[0]);
    }
}


