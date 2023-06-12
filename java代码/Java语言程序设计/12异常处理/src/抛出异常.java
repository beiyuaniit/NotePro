public class 抛出异常 {
    public int Quotient(int a,int b) {
        //只抛出，不处理，给调用者处理
        if(b==0)
            throw new ArithmeticException("除数不能是0...");
        return a/b;

    }
}
