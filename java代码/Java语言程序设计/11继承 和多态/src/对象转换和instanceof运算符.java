public class 对象转换和instanceof运算符 {
    public static void main(String[] args) {
        //只是引用的类型转换（父<->子）、引用所指向对象不变

        /*
         向上转换(隐式转换)：子类对象总是父类的实例（可以用父类创建子类对象
         */
        Object o=new Student();

        /*
         向下转换(显式转换)：
         */
        Student b=(Student)o;
        //更好的是先确定数据类型
        if(o instanceof Student){
            /*
            Student c=(Student)o;
            先转换为子类才能调用子类的方法
            也可以直接(不用创建新对象：
             */
            ((Student)o).out();

        }


        /*
        意义：为了通用程序设计，一个好的方法是把变量定义为父类类型，这样就可以接收所有子类类型
        (先向上转换，再向下转换。先隐式转换，再显式转换
         */
    }
}


class  Student{
    public void out(){
        System.out.println("Hello");
    }
}