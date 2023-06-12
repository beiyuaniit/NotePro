import java.util.Arrays;

public class equals判断相等 {
    public static void main(String[] args) {
        int []a=new int[3];
        int []b=new int[3];
        int []c=new int[3];

        for(int i=0;i<3;i++){
            a[i]=i;
            b[i]=i;
            c[i]=i+1;
        }

        System.out.println(Arrays.equals(a,b));
        System.out.println(Arrays.equals(a,c));

    }
}
