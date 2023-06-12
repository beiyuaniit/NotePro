public class 拿到文件的绝对路径并读取配置文件 {
    public static void main(String[] args) {
        /**
         * 从src根路径出发拿到绝对路径
         * 不能有中文
         */
        /*
        Thread.currentThread():当前线程
        getContextClassLoader()：类加载器
         */
        String path=Thread.currentThread().getContextClassLoader().
                getResource("class.properties").getPath();

        System.out.println(path);
    }
}
