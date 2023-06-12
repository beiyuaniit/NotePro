public class 只让静态代码块执行 {
    /**
     * 静态代码块只在类加载时执行
     */

    public static void main(String[] args) throws ClassNotFoundException {
        //用forName
        Class.forName("myA");
    }
}

class myA {
    static {
        System.out.println("类加载了");
    }

}
