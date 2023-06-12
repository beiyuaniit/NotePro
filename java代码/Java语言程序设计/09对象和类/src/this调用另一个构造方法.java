public class this调用另一个构造方法 {

}

class Circle2{
    //数据私有化
    private double radius;;

    public Circle2(double radius){
        this.radius=radius;
    }

    //调用
    public Circle2(){
        this(3.2);
    }
}
