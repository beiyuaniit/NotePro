package OneToFiveChapter;

import java.util.Arrays;

/**
 * @author: beiyuan
 * @className: 枚举类09
 * @date: 2022/3/7  17:20
 */
public class 枚举类09 {
    public enum Size {samll,small};
    public enum Num{ONE,TWO}
    public static void main(String[] args) {
        System.out.println(Size.samll.equals(Size.small));
        System.out.println(Size.samll.name());
        System.out.println(Size.samll.ordinal());
        System.out.println(Size.samll.hashCode());
        System.out.println(Size.small.hashCode());

        System.out.println(Num.ONE.ordinal());
        System.out.println(Num.TWO.ordinal());

       System.out.println(Arrays.toString(Num.values()));

    }

}

