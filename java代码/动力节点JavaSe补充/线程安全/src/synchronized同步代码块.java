public class synchronized同步代码块 {
    public int sum(int a,int b){
        //对象锁
        synchronized(this){
            int c=a+b;
        }

        //变成了类锁
        synchronized("abc"){
            int d=a+b;
        }
        return a+b;
    }
}
