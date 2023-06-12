public final class 防止继承和重写 {
    //只能主类为最终类:不能作为父类被继承

    //不能被子类重写，可以重载
    public final int  sum(int a,int b){
        return a+b;
    }
}

/*
副类class final I{
}不行
 */
