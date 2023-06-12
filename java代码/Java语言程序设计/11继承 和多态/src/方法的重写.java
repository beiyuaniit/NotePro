public class 方法的重写 {
    public static void main(String[] args) {
        /*
        重写必须相同名字和返回值:重写对方法的实现（否则就是重载
        重载只要相同名字
         */

    }

}

class C{
    public int sum(int a,int b){
        return a+b;
    }
}

class D extends C{
    //重载
    public double sum(double a){
        return a*2;
    }

    //重写
    public int sum(int a,int b){
        return a*b;
    }
}