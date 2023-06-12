public class 重新抛出异常 {
    public static void main(String[] args) throws Exception {
        try{

        }
        catch (Exception th){
            //抛出普通
            //throw th;

            //抛出链式异常（原始 异常+附加信息
            throw new Exception("okkk",th);
        }
    }
}
