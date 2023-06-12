public class 定义类和创建对象 {
    public static void main(String[] args) {
        Circle a=new Circle(2);

        System.out.print(a.getArea());

    }
}

//只能有一个对外公开类
class Circle{
    //属性
    double radius;

    //构造方法
    public Circle(){

    }
    public Circle(double radius){
        this.radius=radius;
    }

    //普通方法
    public double getArea(){
        return radius*radius*Math.PI;
    }

}
