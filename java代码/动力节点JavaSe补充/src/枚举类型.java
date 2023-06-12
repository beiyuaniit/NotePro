public class 枚举类型 {
    public static void main(String[] args) {
        /*
        是一种引用类型
        enum也是当做状态量，比boolean更多的选择
         */
        seaSon a=seaSon.Spring;
        if(a==seaSon.Spring)
            System.out.println("Spring is coming ");
    }
}

//直接列出来就好了
enum seaSon{
    Spring ,Summer ,Autumn,Winter
}

