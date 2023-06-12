public class 线程让位 {
    /**
     * 静态方法
     * 只能让当前线程让位（回到就绪状态
     */
    public static void main(String[] args) {
        Thread.yield();
    }
}

