public class sleep方法 {
    /**
     *因为是静态方法，所以不管对象是什么，都会转为Thread.sleep
     *所以只能使当前线程进入阻塞状态
     */
    public static void main(String[] args) throws InterruptedException {
        //主线程阻塞（毫秒数
        Thread.sleep(1000);
    }
}
