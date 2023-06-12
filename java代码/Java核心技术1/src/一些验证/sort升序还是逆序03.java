package 一些验证;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author: beiyuan
 * @className: sort升序还是逆序
 * @date: 2022/3/11  19:43
 */
public class sort升序还是逆序03 implements Cloneable{



    public static void main(String[] args) {
        Integer []nums1={5,1,3,2,4};
        Integer []nums2={5,1,3,2,4};

        Arrays.sort(nums1,new cmp1());
        Arrays.sort(nums2,new cmp2());

        System.out.println(Arrays.toString(nums1));
        System.out.println(Arrays.toString(nums2));

        int []a={3,1,2};
        Arrays.sort(a);
        System.out.println(Arrays.toString(a));
    }

}

class cmp1 implements Comparator{
    @Override
    public int compare(Object o1, Object o2) {
        return  (int)o1-(int)o2;
    }
}

class cmp2 implements Comparator{
    @Override
    public int compare(Object o1, Object o2) {
        return  (int)o2-(int)o1;
    }
}