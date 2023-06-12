public class 锯齿数组 {
    public static void main(String[] args) {
        //直接赋值
        int [][]a={
                {1,2,3},
                {1,2},
                {1}
        };

        for(int[] e:a){
            for(int em:e){
                System.out.print(em+" ");
            }
        }

        //事先定义
        int [][]b=new int[3][];
        b[0]=new int[3];
        b[1]=new int[2];
        b[2]=new int[1];

    }
}
