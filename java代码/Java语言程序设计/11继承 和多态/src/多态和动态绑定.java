public class 多态和动态绑定 {
    public static void main(String[] args) {
        /*
        多态：使用父类对象的地方都可以使用子类对象代替(子类重写了父类的方法且存在对象转换
             同一个指令，不同的子类有不同的行为
        动态绑定：创建对象时才确定对象的类型(可以用父类创建子类对象
         */

        F a=new F();
        //创建的是G；
        F b=new G();

        int c=sum(a);
        int d=sum(b);

        System.out.println(c);
        System.out.println(d);
    }

    public static int sum(F a){
        return a.getsum();
    }

}

class F{
    int a=3;
    int b=4;
    public int getsum(){
        return a+b;
    }
}

class G extends F{
    //父类的同名属性被隐藏，即使类型不同
    int a=4;
    int b=5;
    public int getsum() {
        return super.a+b;
    }
}
