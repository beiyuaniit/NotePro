/**
 * @author: beiyuan
 * @className: 栈帧演示
 * @date: 2022/2/26  17:02
 */
public class 栈帧演示 {
    /*
    Debug模式下，Frames窗口可以看到
     */
    public static void main(String[] args) {

    }

    public void method1(){
        method1();
    }
    public void method2(){
        int a=1;
    }
}
