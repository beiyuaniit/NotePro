public class 获取父类和接口 {
    public static void main(String[] args) {
        Class c=String.class;

        //父类
        Class superclass=c.getSuperclass();
        System.out.println(superclass.getName());

        //接口
        Class[] interfaces=c.getInterfaces();
        for(Class inter:interfaces){
            System.out.println(inter.getName());
        }
    }
}
