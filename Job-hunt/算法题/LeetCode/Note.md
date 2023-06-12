## 非递归中序遍历

- 非递归
  - 能很好拿到遍历的前一个节点
  - 一直向左遍历，当不是最左时，直接入栈，直到栈顶元素为最左。
  - 取栈顶元素值，root节点等于栈顶元素的右节点
  - 重复直到root为null或者stack为空

```java
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res=new ArrayList<>();
        Stack<TreeNode>stack=new Stack<>();
        TreeNode node=root;  //只是为了不改变原引用
		
        TreeNode pre=root;
        
        while (node!=null || !stack.isEmpty()){  //root或stack不为空
            while (node!=null){
                stack.push(node);  //当前节点入栈
                node= node.left;  //遍历到最左
            }
            node=stack.pop(); //当前节点出栈并取值
            res.add(node.val);
            pre=node;
            node=node.right;//走一步右
        }
        return res;
    }
```

- 递归

## 快速排序

- 

  ```java
  
      // 返回每次基数插入的位置下标
      public static int quicksort(int[] arr, int left, int right) {  //left=0,right=nums.length-1;
          if (left >= right) {  //不能只是等于
              return -1;
          }
          //保存基数
          int basic = arr[left];
          //定义左右指针
          int i = left;
          int j = right;
          while (i < j) {        //左指针小于右指针   大于等于基数的数已经全部放在右边
              while (i < j && arr[j] >=basic) {//操作右指针找到小于基数的下标
                  j--;
              }
              if (i < j) {
                  arr[i] = arr[j];    //将右指针对应小于基数的值放到左指针所指的位置
                  i++;                //左指针自加
              }
              while (i < j && arr[i] < basic) {//相反，找到大于基数的下标
                  i++;
              }
              if (i < j) {
                  arr[j] = arr[i];    //大于基数的值赋给右指针所指的位置
                  j--;                //右指针自减
              }
          }
          arr[i] = basic;                //将基数放入到指针重合处
          quicksort(arr, left, i - 1);    //重复调用，对左半部分数组进行排序
          quicksort(arr, i + 1, right);    //对右半部分数组进行排序
          return i;    //返回插入位置，若有相等元素，可自己选择在相同元素之前或者之后
      }
  ```


## 同余关系

- 若(x-y)%p=0;则称x与y同余    x=y mod p（三等号打不出来）
- 同余情况下
  - x>=0 && y>=0
    - 则 x%p=y%p
  - x<0 && y>=0      或者 x>=0 && y>=0
    - 则(x%p+p) %p=y%p
    - 若x已经是余数   x<p
    - 则(x+p)%p=y%p
  - 当y=r-l时   (x-) 可以移项
    - x=r-l mod p
    - 移项后：x-r=-l mod p
    - 或者 ： x+l=r mod p   用这个吧，都是正数
    - 通过r求l :   l=r-x mod p

- 但是java对于负数 x%p =0  结果都是0。。
  - 通过r求l :   l=r-x mod p
    - 可以换成 l=(r%p-x%p+p)   mod p