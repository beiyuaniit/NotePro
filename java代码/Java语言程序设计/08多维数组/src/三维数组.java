public class 三维数组 {
    public static void main(String[] args) {
        //直接赋值
        int[][][] b={
                {{2,3},{3,4}},
                {{3,1}}
        };

        //事先声明
        int [][][] a=new int[2][2][2];
        a[0][0][0]=4;

    }
}
