import java.util.*;
public class Main {


    public static void main(String[] args) {
        //矩阵个数
        int n=6;
        int []p=new int[]{30,35,15,5,10,20,25}; //矩阵对应的边大小
        //a、b都是要从1开始，0不要。
        int [][]a=new int[n+1][n+1];
        int [][]b=new int[n+1][n+1];

        dp(n,p,a,b);
        System.out.println(a[1][n]);
        showPath(b,1,n);

    }

    private static void showPath(int[][]b,int i,int j ){
        if(i==j){
            //走到缩为一点才打印
            System.out.print("A"+i);
            return;
        }
        System.out.print("(");
        showPath(b,i,b[i][j]);
        showPath(b,b[i][j]+1,j);
        System.out.print(")");
    }
    private static void dp(int n,int []p,int [][]a,int [][]b){
        int min;
        for(int len=2;len<=n;len++){
            //i=2，先算所有长度为2的 如30*35。长度为1，自己到自己为0，就不用显式赋值
            for(int i=1;i<=n-len+1;i++){
                int j=i+len-1;  //长度为len的末尾
                a[i][j]=Integer.MAX_VALUE;
                for(int k=i;k<j;k++){
                    //从i走到k
                    //i j k都是第几个，从1开始，p[i-1]*p[k]*p[j]坐标自己算，从0开始
                    min=a[i][k]+a[k+1][j]+p[i-1]*p[k]*p[j];
                    if(min<a[i][j]){
                        a[i][j]=min;
                        b[i][j]=k;   //从i到j，最后的乘是走k。
                    }
                }
            }
        }
    }
}