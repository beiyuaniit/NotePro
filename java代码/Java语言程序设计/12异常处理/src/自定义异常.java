public class 自定义异常 {
    public static void main(String[] args) {
        /*
        一般用Java提供的异常即可，加入要把出错的信息传递给处理器，则要自己定义
         */
        try{
            throw new myException(-1);
        }
        catch (myException ex){

        }
    }
}

//目前来看，继承异常类即可
class myException extends Exception{
    double radius;

    public myException(double radius){
        this.radius=radius;
    }


}