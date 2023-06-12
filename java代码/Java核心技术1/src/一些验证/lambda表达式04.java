package 一些验证;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author: beiyuan
 * @className: lambda实现字符串长度排序04
 * @date: 2022/3/11  22:31
 */
public class lambda表达式04 {
    public static void main(String[] args) {
        //实现按照String长度排序
        Comparator<String > comp=(str1,str2)->{
            return str1.length()-str2.length();
        };

        String[]strs={"like","sun","moonlight","a","is"};
        Arrays.sort(strs,comp);

        System.out.println(Arrays.toString(strs));//[a, is, sun, like, moonlight]

        int i=0;
        int value;
        Comparator<Integer> cmp=(num1,num2)->{
            //i++;  //不可以被修改
            int j=i;//可以访问
            //int value;//不能与外部变量同名
            //Integer num1;//不能与参数同名
            j++;
            return num1-num2;
        };

    }

    protected class A{

    }
}
