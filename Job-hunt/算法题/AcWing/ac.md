## 2.0-1背包问题(O(n2))-只能拿一次

- 问题描述

- 每个物品只能放一个

  有 NN件物品和一个容量是 VV的背包。每件物品只能使用一次。

  第 ii 件物品的体积是 vi，价值是 wi。

  求解将哪些物品装入背包，可使这些物品的总体积不超过背包容量，且总价值最大。
  输出最大价值。

- 输入格式

  第一行两个整数，N，VN，V，用空格隔开，分别表示物品数量和背包容积。

  接下来有 N 行，每行两个整数 vi,wivi,wi，用空格隔开，分别表示第 ii 件物品的体积和价值。

- 输出格式

  输出一个整数，表示最大价值。

- 数据范围

  0<N,V≤10000<N,V≤100
  0<vi,wi≤10000<vi,wi≤1000

- 输入样例

```
4 5
1 2
2 4
3 4
4 5
```

- 输出样例：

```
8
```

- dp

  - 从后往前dp
  - 从前往后dp就是完全背包问题了

  ```java
  import java.util.*;
  public class Main{
      //对容量从0-V都dp
      public static void main(String []args){
          Scanner in=new Scanner(System.in);
          int N=in.nextInt();
          int V=in.nextInt();
          int []w=new int[N];
          int []v=new int[N];
          for(int i=0;i<N;i++){
              w[i]=in.nextInt();
              v[i]=in.nextInt();
          }
          int []dp=new int[V+1]; //因为下面j要等于V，也就是背包的最大值。
          //dp，不断对当前体积为w[i]的放入，更新dp数组的每个值
          for(int i=0;i<N;i++){
              //j要从大到小，比如第一轮的时候，前面的必须为0.
              //如果从小到大，则前面可能不为0，后面再拿到时就重复了。
              //如背包大小为5，第一体积为2，价值为4，则dp[2]=2,dp[4]=4。重复计算了
              for(int j=V;j>=0;j--){
                  if(j>=w[i]){
                      dp[j]=Math.max(dp[j],dp[j-w[i]]+v[i]);
                  }
              }
          }
          System.out.println(dp[V]);
      }
  }
  ```

## 3.完全背包问题-次数没有限制

- 每个物品的个数无限

- 注释看0-1背包问题

- dp

  ```java
  import java.util.*;
  public class Main{
      public static void main(String []args){
          Scanner in=new Scanner(System.in);
          int N=in.nextInt();
          int V=in.nextInt();
          int []w=new int[N];
          int []v=new int[N];
          for(int i=0;i<N;i++){
              w[i]=in.nextInt();
              v[i]=in.nextInt();
          }
          int []dp=new int[V+1];
          for(int i=0;i<N;i++){
              //从前往后dp
              for(int j=0;j<=V;j++){
                  if(j>=w[i]){
                      dp[j]=Math.max(dp[j],dp[j-w[i]]+v[i]);
                  }
              }
          }
          System.out.println(dp[V]);
      }
  }
  ```


## 4.多重背包问题-次数有限制

- 每个物品有自己的个数限制

- 跟0-1背包问题相似，不过增加了判断能拿多个的情况

- dp

  - O(n3)

  ```java
  import java.util.*;
  public class Main{
      public static void main(String []args){
          Scanner in=new Scanner(System.in);
          int N=in.nextInt();
          int V=in.nextInt();
          int []w=new int[N];
          int []v=new int[N];
          int []s=new int[N];
          for(int i=0;i<N;i++){
              w[i]=in.nextInt();
              v[i]=in.nextInt();
              s[i]=in.nextInt();
          }
          int []dp=new int[V+1];
          for(int i=0;i<N;i++){
              for(int j=V;j>=0;j--){
                  //从后往前拿拿一个，可以增加几个
                  //跟0-1背包问题相似，不过增加一个计数。
                  for(int k=1;k<=s[i];k++){
                      int t=k*w[i];
                      if(j>=t){
                          dp[j]=Math.max(dp[j],dp[j-t]+v[i]*k);
                      }else{
                          break;
                      }
                  }
                 
              }
          }
          System.out.println(dp[V]);
      }
  }
  ```


## 5.多重背包问题二-数据规模比四大

- 用二进制优化

- 不过还是超时了，不知道是不是用了java而没有用c的原因

  ```java
  import java.util.*;
  public class Main{
      //根据二进制打包成更大的小包。所有s[i]都可以由这些小包构成
      //不是每位都取，11 = 1+2+8，这样无法表示4，5，6等可选择数字
      // 要想拿到1-N之间的任何数，则一般最大的一位不能取11=1 2 4 4   8=1 2 4 1 
      public static void main(String []args){
          Scanner in=new Scanner(System.in);
          int N=in.nextInt();
          int V=in.nextInt();
          int []w=new int[N];
          int []v=new int[N];
          int []s=new int[N];
          int sum=0;//小包个数
          for(int i=0;i<N;i++){
              w[i]=in.nextInt();
              v[i]=in.nextInt();
              s[i]=in.nextInt();
              sum+=s[i];
          }
          int []dp=new int[V+1];
          int []a=new int[sum];//重量
          int []b=new int[sum];//价值
          int x=0;//下标
          for(int i=0;i<N;i++){
              //  每位都取，错误
              // for(int j=0;j<32;j++){//s[i]右移的位置，用于判断某位是否为1
              //     //&是所有位都与，返回一个int
              //     if(((s[i]>>j)&1)==1){
              //         a[x]=w[i]*(1<<j);
              //         b[x]=v[i]*(1<<j);
              //         x++;
              //     }
              // }
              for(int j=1;j<s[i];j<<=1){
                  a[x]=w[i]*j;
                  b[x]=v[i]*j;
                  s[i]-=j;
                  x++;
              }
              //最后还有的话还要处理下
              if(s[i]>0){
                  a[x]=w[i]*s[i];
                  b[x]=v[i]*s[i];
                  x++;
              }
          }
          for(int i=0;i<sum;i++){
              for(int j=V;j>=a[i];j--){
                  //每个小包只能拿一次，所以是0-1背包问题
                  dp[j]=Math.max(dp[j],dp[j-a[i]]+b[i]);
              }
          }
          System.out.println(dp[V]);
      }
  }
  ```

  

## 101. 最高的牛（差分数组）

有 NN 头牛站成一行，被编队为 1、2、3…N1、2、3…N，每头牛的身高都为整数。

当且仅当两头牛中间的牛身高都比它们矮时，两头牛方可看到对方。

现在，我们只知道其中最高的牛是第 PP 头，它的身高是 HH ，剩余牛的身高未知。

但是，我们还知道这群牛之中存在着 MM 对关系，每对关系都指明了某两头牛 AA 和 BB 可以相互看见。

求每头牛的身高的最大可能值是多少。

- 输入格式

第一行输入整数 N,P,H,MN,P,H,M，数据用空格隔开。

接下来 MM 行，每行输出两个整数 AA 和 BB ，代表牛 AA 和牛 BB 可以相互看见，数据用空格隔开。

- 输出格式

一共输出 NN 行数据，每行输出一个整数。

第 ii 行输出的整数代表第 ii 头牛可能的最大身高。

- 输入样例：

```
9 3 5 5
1 3
5 3
4 3
3 7
9 8
```

- 输出样例：

```
5
4
5
3
4
4
5
5
5
```

- 注意：

- 此题中给出的关系对可能存在重复

- 差分数组链接：https://blog.csdn.net/qq_51705526/article/details/125980719

  ```java
  //差分数组 b[0]=0,b[i]=a[i]-a[i-1]
  //记录比最高的小多少
  import java.util.*;
  public class Main{
      public static void main(String[]args){
          Scanner in=new Scanner(System.in);
          int n=in.nextInt();
          int p=in.nextInt();
          int h=in.nextInt();
          int m=in.nextInt();
  
          int[]B=new int[n+1];
          B[1]=h;
          HashSet<String>set=new HashSet<>();
          for(int i=0;i<m;i++){
              int a=in.nextInt();
              int b=in.nextInt();
              //a=Math.min(a,b);
              //b=Math.max(a,b);  这样换a可能已经变了
              if(a>b){
                  int t=a;
                  a=b;
                  b=t;
              }
              //[a+1,b-1]都减一
              if(!set.contains(a+"-"+b)){
                  B[a+1]--;
                  B[b]++;//抵消前面的--，到此处终止
                  set.add(a+"-"+b);
              }
          }
          //前缀和
          for(int i=1;i<=n;i++){
              B[i]+=B[i-1];
              System.out.println(B[i]);
          }
      }
  }
  
  
  ```

  