public class for的简便用法 {
    public static void main(String[] args) {
        int[] arr=new int[10];

        for(int i=0;i<10;i++){
            arr[i]=i;
        }

        for(int e:arr){
            System.out.println(e);
        }
    }
}
