public class 自定义泛型<T> {
    //T继承Number,并把结果double转化为T类型
    public <T extends Number> double sum(T a,T b){
        return a.doubleValue()+b.doubleValue();
    }

    T a;
    public T getT(){
        return a;
    }
}
