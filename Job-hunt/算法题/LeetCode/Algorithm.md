# ==基础==

## ==二分==

### 二分查找

- 升序整型数组 nums 和 target  ,返回下标
- low<=high   mid一加一减

```java
 public int search(int[] nums, int target) {
        int low=0;
        int high=nums.length-1;
        while(low<=high){//等于时还可再判断一次
            int mid=low+((high-low)>>1);//防止溢出。要加最外的括号
            if(nums[mid]<target){
                low=mid+1;
            }else if(nums[mid]>target){
                high=mid-1;
            }else{
                return mid;//至多有一次成立，最后判断
            }
        }
        return -1;
 }
```

### 二分插入（可结合排序）

- 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
- 分界点可以用low<high    low+1

```java
public int searchInsert(int[] nums, int target) {
        int l=0;
        int r=nums.length;
        while(l<r){
            int mid=l+((r-l)>>1);
            if(nums[mid]>=target){
                r=mid;
            }else{
                l=mid+1;
            }
        }
        return l;//low收缩到等于target（等于时也是最左那个）或第一个大于target的数
    }
```

## ==排列==

### 有重复

- 给定一个可包含重复数字的序列 `nums` ，***按任意顺序*** 返回所有不重复的全排列
- 回溯复原，并去重

```java
    List<List<Integer>>res=new ArrayList<>();
    List<Integer>list=new ArrayList<>();

    boolean[]v;

    public List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        v=new boolean[nums.length];
        getRes(nums,0);
        return res;
    }

    private void getRes(int [] nums,int n){
        if(n==nums.length){
            //排列是全都要。结果一定是在最后处理
            //v[]全是true，并且结果只是顺序不同
            res.add(new ArrayList<>(list));
            return;
        }

        //所有未访问的都要访问
        for(int i=0;i<nums.length;i++){
            //去重
            if(i>0 && v[i-1] &&  nums[i]==nums[i-1]){ //去重
                continue;
            }
            if(!v[i]){
                //每次选一个放在末尾，并用v[i]记录已经访问。要用list，因为排列只是顺序不同，但要用到所有元素
                v[i]=true;
                list.add(nums[i]);
                getRes(nums,n+1);
                list.remove(list.size()-1);
                v[i]=false;
            }
        }
    }
```

- show

  ```java
  input:[1,2,3]
  output:[[1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1]]
  ```

  

## ==组合==

### 只选位置

- 给定两个整数 `n` 和 `k`，返回范围 `[1, n]` 中所有可能的 `k` 个数的组合。你可以按 **任何顺序** 返回答案
- 复原回溯

```java
import java.util.*;
public class Main {
    static int ans=0;
    static int n;
    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        n=in.nextInt();
        int []nums=new int[n];
        boolean []v=new boolean[n];
        for(int i=0; i<nums.length; i++){
            nums[i]=in.nextInt();
        }
       // Arrays.sort(nums);
        dfs(nums,0,v);
        System.out.println(ans);
    }

    private static void dfs(int[]nums,int ix ,boolean[]v){
        check(nums,v);
        
        //这个位置无非是选和不选的问题，也可以用list.add(),list.remove()
        for(int i=ix;i<nums.length;i++){
           //这里都是i
           v[i]=true;
           dfs(nums,i+1,v);
           v[i]=false;
        }
    }

    //任何操作
    private static void check(int []nums ,boolean[]v){
    	...	
    }
}
```

## ==动态规划==

### ==一维==

#### 第n个丑数

- 给你一个整数 `n` ，请你找出并返回第 `n` 个 **丑数** 。

  - **丑数** 就是只包含质因数 `2`、`3` 和/或 `5` 的正整数。

- dp

  ```java
  class Solution {
      //三指针，记录2，3，5要乘的数。每个数只能够乘一次，所以可以递增
      public int nthUglyNumber(int n) {
          long []dp=new long [n];
          dp[0]=1;
          int p2=0;
          int p3=0;
          int p5=0;
          for(int i=1;i<n;i++){
              long a2=dp[p2]*2;
              long a3=dp[p3]*3;
              long a5=dp[p5]*5;
              long min=Math.min(a2,Math.min(a3,a5));
              dp[i]=min;
              //若等于则++，为了防止重复
              if(a2==min){
                  p2++;
              }
              if(a3==min){
                  p3++;
              }
              if(a5==min){
                  p5++;
              }
          }
          return (int)dp[n-1];
      }
  }
  ```

- 优先队列
  - 空间复杂度更高，调整最小堆也要花销

### ==填二维表==

#### 矩阵连乘

- 找最小代价的矩阵相乘顺序

- 思路

  - 先计算所有相邻的2个矩阵相乘，再计算长度为3的....以此类推

- 实现

  ```java
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
          System.out.println(a[1][n]);//15125
          showPath(b,1,n);//((A1(A2A3))((A4A5)A6))
  
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
  ```

  

# ==进阶==

## ==离散化==

- 基本思想
  - 把无限空间中有限的个体映射到有限的空间中去，以此提高算法的时空效率
  - 离散化是在不改变数据相对大小的条件下，对数据进行相应的缩小。
  - 一个离散化的例子（可惜是c++）：https://blog.csdn.net/zhang_dehong/article/details/128393910
    - 数据的范围是0到10^9，我们总不可能开一个10^9的数组再遍历然后加数

### 城市正视图（南墙可见性）

- 来源
  - 算法竞赛入门指南第二版P132
    - https://blog.csdn.net/qq_45670253/article/details/123300983
    - https://blog.csdn.net/weixin_42193704/article/details/82181490
  - 墙在南面。
- 离散化
  - 把无穷变为有穷状态
    - 因为不可能枚举建筑正面上的所有坐标来判断可见性
  - 可以忽略深度，因为可以通过y来判断
  - 将x坐标排序去重，则建筑在任意相邻的两个x坐标中的区间，要么全部可见，要么全部不可见
  - 只需要选择这个区间中的一个点（如中点mx），就可以判断这个建筑是否可见了
  - 可见判断：建筑包含mx，南面没有建筑也包含mx 且不比它矮 。（b[i]不包含mx则表示b[i]在mx处不可见）                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
- 输出
  - 建筑的俯视图左下角x、y，宽度w（x方向长度），深度d(y方向长度)，高度h

- 输出

  - 可见的建筑

- 实现

  ```java
  import java.util.*;
  public class Main {
      static class Building{
          int id;
          double x,y,w,d,h;
      }
  
      static int n;
      static Building []b;
      static double[]x;
  
  
      /**
       * input:
       * 14
       * 160 0 30 60 30
       * 125 0 32 28 60
       * 95 0 27 28 40
       * 70 35 19 55 90
       * 0 0 60 35 80
       * 0 40 29 20 60
       * 35 40 25 45 80
       * 0 67 25 20 50
       * 0 92 90 20 80
       * 95 38 55 12 50
       * 95 60 60 13 30
       * 95 80 45 25 50
       * 165 65 15 15 25
       * 165 85 10 15 35
       * output:
       * 5 9 4 3 10 2 1 14
       * @param args
       */
      public static void main(String[] args) {
  
  
          Scanner in=new Scanner(System.in);
          n=in.nextInt();//n个建筑
          b=new Building[n];
          for(int i=0;i<n;i++){
              b[i]=new Building();
          }
          x=new double[n*2];  //保存x坐标
          for(int i=0;i<n;i++){
              b[i].id=i+1;
              b[i].x=in.nextDouble();b[i].y=in.nextDouble();
              b[i].w=in.nextDouble();b[i].d=in.nextDouble();
              b[i].h=in.nextDouble();
              //一个建筑有两个x坐标
              x[i*2]=b[i].x;
              x[i*2+1]=b[i].x+b[i].w;
          }
  
          //对建筑根据x从小到大排序，若x相等，则根据y
          Arrays.sort(b,(x,y)->{
              return x.x!=y.x ? (int)(x.x-y.x) : (int)(x.y-y.y);
          });
          //对x排序并去重,得到m+1个坐标
          Arrays.sort(x);
          int m=1;
          for(int i=1;i<x.length;i++){
              if(x[i]!=x[i-1]){
                  x[m]=x[i];
                  m++;
              }
          }
  
          for(int i=0;i<n;i++){
              boolean v=false;
              for (int j=1;j<=m;j++){
                  double mx=(x[j]+x[j-1])/2;
                  //只要符合建筑b[i]在一个区间可见即可
                  v |=visible(i,mx);
              }
              if(v){
                  System.out.print(b[i].id+" ");
              }
          }
  
      }
      //建筑b[i]是否包含区间中点mx
      private static boolean cover( int i,double mx){
          return mx>=b[i].x && mx<=b[i].x+b[i].w;
      }
  
      //建筑b[i]在区间中点mx处是否可见
      private static boolean visible(int i,double mx){
          if(!cover(i,mx)){
              return false;
          }
          for(int j=0;j<n;j++){
              //j在i前面，j比i高，j包含mx
              if(b[j].y<b[i].y && b[j].h>=b[i].h && cover(j,mx)){
                  return false;
              }
          }
          return true;
      }
  }
  ```

  

