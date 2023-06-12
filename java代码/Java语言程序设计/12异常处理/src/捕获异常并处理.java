public class 捕获异常并处理 {
    public static void main(String[] args) {
        /*
        try语句中可能出现异常
        try如果出现了异常，直接跳到catch语句并处理
        （不手动抛出(throw)异常的话，JVM会自动抛出
        (一层层往上抛(抛给调用者,如果最终没有处理则在控制台打印信息
        (声明了必检，则一定要处理
        */

        抛出异常 a=new 抛出异常();
        try{
            a.Quotient(2,0);
        }
        catch (ArithmeticException ex){
            System.out.println("出错啦,除数不能是0");
        }
    }
}
