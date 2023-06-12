public class 重载方法 {
    public static int sum(int a,int b){
        return a+b;
    }

    //直接重载就好了
    public static double sum(double a,double b){
        return a+b;
    }
    public static void main(String[] args) {
        System.out.println(sum(1,2));
        System.out.println(sum(1.2,2.1));
    }
}
