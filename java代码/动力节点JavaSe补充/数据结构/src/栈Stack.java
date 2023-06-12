import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class 栈Stack {
    public static void main(String[] args) {
        Stack stack = new Stack();
        /*
         可以存放不同类型的对象的引用
         */
        int a=4;
        double b=3.13;
        stack.push(a);
        stack.push(b);

        System.out.println(stack.pop());
        System.out.println(stack.pop());



    }
}
