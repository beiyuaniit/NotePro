import java.util.Arrays;

public class 数组排序和查找 {
    public static void main(String[] args) {
        int []a=new int[5];
        a[0]=1;a[1]=3;a[2]=2;a[3]=5;a[4]=4;

        //只能升序吗。。。
        Arrays.sort(a);

        for(int e:a){
            System.out.println(e);
        }

        //对升序数组进行二分法查找,有则返回下标，没有就返回负数
        System.out.println(Arrays.binarySearch(a,3));
        System.out.println(Arrays.binarySearch(a,9));
    }
}
