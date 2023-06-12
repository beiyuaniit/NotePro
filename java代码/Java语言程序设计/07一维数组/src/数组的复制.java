public class 数组的复制 {
    public static void main(String[] args) {
        int []a=new int [3];
        int []b=new int [3];

        for(int i=0;i<3;i++){
            a[i]=i+1;
        }

        //只是把a引用给了b，可以修改a的值
        //并不是真正的复制
        b=a;
        a[2]=4;

        for(int e:b){
            System.out.println(e);
        }

        //这个方法不错，for循环自己复制也行
        int [] c=new int [3];
        System.arraycopy(a,0,c,0,a.length);

        a[2]=2;
        c[2]=3;
        for(int e:c){
            System.out.println(e);
        }

    }
}
