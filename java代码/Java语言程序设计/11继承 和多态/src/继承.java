public class 继承 {
    //extends关键字即可
    public static void main(String[] args) {
        Circle1 a=new Circle1();

    }

}

class Circle{
    int a=3;

}

class Circle1 extends Circle{
    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    int b;
}