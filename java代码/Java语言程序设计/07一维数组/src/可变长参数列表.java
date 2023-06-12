public class 可变长参数列表 {
    public static void main(String[] args) {

        //只能是相同类型

        int []a=new int[3];
        a[0]=4;a[1]=5;a[2]=6;

        //可以传递任意个参数
        visit(1,2,3);

        //也可以传递数组
        visit(a);


    }

    //接收参数时当做数组
    public static void visit(int...num){
        for(int e:num){
            System.out.println(e);
        }
    }
}
