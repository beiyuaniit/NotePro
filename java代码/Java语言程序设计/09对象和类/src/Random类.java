import java.util.Random;

public class Random类 {
    public static void main(String[] args) {
        //默认以当前时间为种子
        Random ran1=new Random();
        //以3为种子
        Random ran2=new Random(3);

        //随机int
        int a=ran1.nextInt();

        //int(0到9，不包括9
        int b=ran1.nextInt(9);

        //double
        double c=ran1.nextDouble();

        //boolean
        boolean d=ran1.nextBoolean();

        //某区间的随机数：取模+差值

    }
}
