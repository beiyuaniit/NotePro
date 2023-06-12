package pro_com;

import org.junit.Test;

/**
 * @author: beiyuan
 * @date: 2023/2/25  21:11
 */
public class GuardedSuspension {

    @Test
    public void testGuard() throws InterruptedException {
        Guarded guarded=new Guarded();
        Thread t1=new Thread(()->{
                try {
                    System.out.println("result:"+guarded.getResult());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
        },"t1");

        Thread t2=new Thread(()->{
                guarded.setResult(new Object());
        },"t2");
        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
    class Guarded{
        private Object result;

        public void setResult(Object result) {
            this.result=result;
            synchronized (this){
                this.notifyAll();
            }
        }

        public Object getResult() throws InterruptedException {
            if(result==null){
                while (true){
                    synchronized (this){
                        this.wait();
                    }
                    break;
                }
            }
            return result;
        }
    }
}
