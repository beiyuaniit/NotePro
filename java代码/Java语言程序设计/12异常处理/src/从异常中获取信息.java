public class 从异常中获取信息 {
    public static void main(String[] args) {
        try{
            int a=1/0;
        }
        catch (ArithmeticException ex){
            //返回描述信息
            System.out.println(ex.getMessage());
            System.out.println(ex.toString());

            //返回堆栈跟踪元素数组
            System.out.println(ex.getStackTrace());

            //打印Throwable对象以及它的调用堆栈信息
            ex.printStackTrace();
        }
    }
}
