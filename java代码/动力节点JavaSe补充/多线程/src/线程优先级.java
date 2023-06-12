public class 线程优先级 {
    /**
     * 最高10：Thread.MAX_PRIORITY
     * 默认5：Thread.NORM_PRIORITY
     * 最低1：Thread.MIN_PRIORITY
     */

    public static void main(String[] args) {
        //获取当前线程对象并设置优先级
        Thread.currentThread().setPriority(7);

    }
}
