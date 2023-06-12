//import java.util.*;
//public class Main01 {
//    static int ans=0;
//    static int n;
//    static int l;
//    static int r;
//    static int x;
//
//    static List<Integer>list;
//    public static void main(String[] args) {
//        Scanner in=new Scanner(System.in);
//        n=in.nextInt();
//        l=in.nextInt();
//        r=in.nextInt();
//        x=in.nextInt();
//
//        int []nums=new int[n];
//        list=new ArrayList<>();
//        for(int i=0; i<nums.length; i++){
//            nums[i]=in.nextInt();
//        }
//
//        // Arrays.sort(nums);
//        dfs(nums,0);
//        System.out.println(ans);
//    }
//
//    private static void dfs(int[]nums,int ix){
//        check(nums,v);
//        for(int i=ix;i<nums.length;i++){
//            v[i]=true;
//            dfs(nums,ix+1,v);
//            v[i]=false;
//        }
//    }
//
//    private static void check(int []nums ,boolean[]v){
//        int min=(int)Math.pow(10,7);
//        int sum=0;
//        int max=-1;
//        int count=0;
//        for(int i=0; i<nums.length; i++){
//            if(v[i]){
//                count++;
//                sum+=nums[i];
//                min=Math.min(min,nums[i]);
//                max=Math.max(max,nums[i]);
//            }
//        }
//        if(count>=2 && sum>=l && sum<=r && max-min>=x){
//            ans++;
//            System.out.println(Arrays.toString(v));
//        }
//    }
//}