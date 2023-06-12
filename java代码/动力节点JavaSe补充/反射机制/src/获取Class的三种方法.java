public class 获取Class的三种方法 {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        //第一种
        //c1代表String.class文件，或者说代表String类型
        Class c1=Class.forName("java.lang.String");


        //第二种，
        //jvm只加载一份字节码文件
        //Object的getClass()
        Integer a = 3;
        Class c2=a.getClass();

        //第三种，类型的class属性
        Class c3=int.class;




    }
}
