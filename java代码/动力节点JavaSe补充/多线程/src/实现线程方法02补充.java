public class 实现线程方法02补充 {
    /**
     * 采用匿名内部类
     */
    public static void main(String[] args) {
        //直接匿名内部类创建对象，实现Runnable的run
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });

        //启动线程
        t.start();
    }
}
