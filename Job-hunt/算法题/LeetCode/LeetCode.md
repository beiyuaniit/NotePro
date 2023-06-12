#  数据结构

## ==HashSet==

### 217. 存在重复元素

- 给你一个整数数组 `nums` 。如果任一值在数组中出现 **至少两次** ，返回 `true` ；如果数组中每个元素互不相同，返回 `false` 
- HashSet

```java
    public boolean containsDuplicate(int[] nums) {
        //HashSet，无需不重复。
        Set<Integer> set=new HashSet();
        for(int num:nums){
            if(set.contains(num)){
                return true;//有重复则返回
            }else{
                set.add(num);
            }
        }
        return false;
    }
```

- 排序。若相邻的相等，true

### 36.有效的数独

- `9 x 9` 的数独，行、列、九宫格都不能有重复。一个有效的数独（部分已被填充）不一定是可解的。
- HashSet+暴力

```java
    public boolean isValidSudoku(char[][] board) {
        Set [] Cen=new HashSet[3];//九宫格
        for(int i=0;i<3;i++){
            Cen[i]=new HashSet();
        }
        Set [] Row=new HashSet[9];//列
        Set [] Line=new HashSet[9];//行
        for(int i=0;i<9;i++){
            Row[i]=new HashSet();
            Line[i]=new HashSet();
        }
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board.length;j++){
                if(board[i][j]=='.'){
                    continue;
                }
                Character c=board[i][j];
                if(!Line[i].contains(c)){//行
                    Line[i].add(c);
                }else {
                    return false;
                }

                if(!Row[j].contains(c)){
                    Row[j].add(c);
                }else {
                    return false;
                }
                int index=j/3;
                if(!Cen[index].contains(c)){//九宫格
                    Cen[index].add(c);
                }else {
                    return false;
                }
            }
            if(i%3==2){
                for(int k=0;k<3;k++){
                    Cen[k].clear();
                }
            }
        }
        return true;
    }
```

### 03.数组中重复的数(剑指)

- 在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字重复了，也不知道每个数字重复了几次。请找出数组中任意一个重复的数字。
- HashSet判断重复

```java
//感觉最实用
class Solution {
    public int findRepeatNumber(int[] nums) {
        HashSet <Integer> hset=new HashSet<>();

        for(int i:nums){
            if(hset.contains(i)){
                return i;
            }
            hset.add(i);
        }
        return -1;//最后随便返回个数保证能通过编译
    }
}
```

- 排序后检查

```java
class Solution {
    public int findRepeatNumber(int[] nums) {
        Arrays.sort(nums);
        for(int i=0;i<nums.length-1;i++){
            if(nums[i]==nums[i+1])
            return nums[i];
        }
        return -1;
    }
}
```

- 原地交换,让元素和下标一一对应:因为n-1

### 128.最长连续序列

- 未排序nums，序列的位置可不连续，要求时间O(n)
- HashSet
  - 先都放Set+判断一边

```java
    //直接Set。。是个鬼并查集
    public int longestConsecutive(int[] nums) {
        Set<Integer>set=new HashSet<>();
        //先全部放才好下面的只判断x之后
        for(int n:nums){
            set.add(n);
        }
        int res=0;
        for (int num : nums) {
            //if(!set.contains(nums[i])){
            //   set.add(nums[i]);
            //}
            //set.add(num);//会自动去重
            int count = 1;
            //只枚举后，6。x.x+1.x+2..反正这个序列以x开头
            if(!set.contains(num-1)){
                int temp = num;
                while (set.contains(++temp)) {
                    count++;
                }
                //更新res
                if (count > res) {
                    res = count;
                }
            }

        }
        return res;
    }
```



## ==HashMap==

### 350.两个数组的交集

- 返回结果中每个元素出现的次数，应与元素在两个数组中都出现的次数一致（如果出现次数不一致，则考虑取较小值）。
  - nums1 = [1,2,2,1], nums2 = [2,2]  result=[2,2]               //也就是一一抵消
- HashMap

```java
 public int[] intersect(int[] nums1, int[] nums2) {
        ArrayList<Integer> arr=new ArrayList<>();
        HashMap<Integer,Integer> map=new HashMap<>();
        //把nums1存进去
        for(int i:nums1){
            if(!map.containsKey(i)){
                map.put(i,1);
            }else{
                map.put(i,map.get(i)+1);
            }
        }
        //依次判断nums2
        for(int j:nums2){
            if(map.containsKey(j)&&map.get(j)>0){
                arr.add(j);
                map.put(j,map.get(j)-1);
            }
        }
        //转为整型数组
        int []nums=new int[arr.size()];
        for(int i=0;i<arr.size();i++){
            nums[i]=arr.get(i);
        }
        return nums;
    }
```

### 35.复杂链表的复制(剑指)

- 请实现 copyRandomList 函数，复制一个复杂链表。在复杂链表中，每个节点除了有一个 next 指针指向下一个节点，还有一个 random 指针指向链表中的任意节点或者 null。
- 难点：random指向的对象可能还没有创建
- 用数组保存指向的对象下标

```java
//O(n2)
/*
// Definition for a Node.
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
*/
//暴力法
class Solution {
    public Node copyRandomList(Node head) {
        if(head==null){
            return null;
        }

        //一重while()拿到总个数
        Node temp=head;
        int count=0;
        while(temp!=null){
            count++;
            temp=temp.next;
        }

        //两重while()拿到random下标,并存入数组
        Node temp1=head;
        int []indexs=new int[count];
        int index=0;
        while(temp1!=null){
            //特殊情况指向null
            if(temp1.random==null){
                indexs[index++]=count;
                temp1=temp1.next;
                continue;
            }

            Node temp2=head;
            int i=0;//记录temp2的下标
            while(temp2!=null){
                if(temp2==temp1.random){
                    indexs[index++]=i;
                    break;
                }
                temp2=temp2.next;
                i++;
            }
            temp1=temp1.next;
        }

        //创建链表，用数组存。除了random都初始化
        Node temp3=head;
        Node[]nodes=new Node[count];
        for(int j=0;j<count;j++){
            nodes[j]=new Node(temp3.val);
            temp3=temp3.next;
        }

        //给链表random引用赋值
        for(int k=0;k<count;k++){
            if(k==count-1){
                nodes[k].next=null;
            }
            else{
                nodes[k].next=nodes[k+1];
            }

            if(indexs[k]==count){
                nodes[k].random=null;
            }else{
                nodes[k].random=nodes[indexs[k]];
            }
               
        }
        return nodes[0];       
    }
}
```

- HashMap把一一对应变为两两对应关系

```java
/*
O(n)
每个节点都是2个元素，由一一对应变成了二二对应关系
key存放旧的，value创建一模一样新的
*/
class Solution {
    public Node copyRandomList(Node head) {
        if(head==null){
            return null;//防止空指针异常
        }

        HashMap<Node,Node>map=new HashMap<>();
        Node cur=head;
        while(cur!=null){
            map.put(cur,new Node(cur.val));
            cur=cur.next;
        }

        cur=head;//cur已经没有用，可重复利用
        while(cur!=null){
            //给next和random一起赋值
            map.get(cur).next=map.get(cur.next);
            map.get(cur).random=map.get(cur.random);
            cur=cur.next;
        }

        return map.get(head);
    }
}
```

### 1.两数之和

- 给定一个整数数组 `nums` 和一个整数目标值 `target`，请你在该数组中找出 **和为目标值** *`target`* 的那 **两个** 整数，并返回它们的数组下标。
- HashMap

```java
    public int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> map=new HashMap<>();
        for(int i=0;i<nums.length;i++){
            if(map.containsKey(target-nums[i])){
                return new int []{map.get(target-nums[i]),i};
            }else{
                map.put(nums[i],i);
            }
        }
        return new int [0];
    }
```

### 30.串联所有单词的字串

- 串联，顺序可不一致。`s` 中的 串联子串是指一个包含 `words` 中所有字符串以任意顺序排列连接起来的子串
  - 例如，如果 words = ["ab","cd","ef"]， 那么 "abcdef"， "abefcd"，"cdabef"， "cdefab"，"efabcd"， 和 "efcdab" 都是串联子串。 "acdbef" 不是串联子串，因为他不是任何 words 排列的连接。


```java
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res=new ArrayList<>();
        int size=words.length*words[0].length();
        int n=words[0].length();
        for(int i=0;i+size<=s.length();i++){
            //word会重复
            Map<String, Integer>map=new HashMap<>();
            for(int j=i;j+n<=i+size;j+=n){
                String word=s.substring(j,j+n);
                if(!map.containsKey(word)){
                    map.put(word,1);//map存放数量
                }else {
                    map.put(word,map.get(word)+1);
                }
            }
            //根据剩余数量匹配
            for (int j=0;j<words.length;j++){
                if(!map.containsKey(words[j])|| map.get(words[j])<1){
                    break;
                }
                map.put(words[j],map.get(words[j])-1);
                if(j==words.length-1){
                    res.add(i);
                }
            }
        }
        return res;
    }
```

### 187.重复的DNA序列

- 对于一些小的字符串或字符，可以考虑用int来存储。返回所有在 DNA 分子中出现不止一次的 长度为 10 的序列(子字符串)
- 直接暴力HashMap居然可以

```java
    static final int L=10;
    public List<String> findRepeatedDnaSequences(String s) {
        Map<String,Integer>map=new HashMap<>();
        List<String>res=new ArrayList<>();
        for(int i=0;i+L<=s.length();i++){
            String str=s.substring(i,i+L);
            map.put(str,map.getOrDefault(str,0)+1);
            if(map.get(str)==2){
                res.add(str);
            }
        }
        return res;
    }
```

### 41.缺失的第一个正数

- 给你一个未排序的整数数组 `nums` ，请你找出其中没有出现的最小的正整数。时间O(n),常数空间，这么要求一般是要该原引用了
- nums中的数没有要求
- 下标位置一一对应法。把数组设计成HashSet

```java
    //缺失的只能是1~nums.length+1,所以交换放到对应下标位置。遍历后nums[i]!=i+1就是
    //不想改变原数组的话就自己再创造一个
	//其他的不用管
    public int firstMissingPositive(int[] nums) {
        for(int i=0;i<nums.length;i++){
            while (nums[i]>0 && nums[i]<=nums.length &&
                    i!=nums[i]-1 && nums[i]!=nums[nums[i]-1]){//相同则不换，否则出不了循环
                swap(nums,i,nums[i]-1);
            }
        }
        for(int i=0;i<nums.length;i++){
            if(nums[i]!=i+1){
                return i+1;
            }
        }

        return nums.length+1;
    }

    private void swap(int[] nums,int i,int j){
        int temp=nums[i];
        nums[i]=nums[j];
        nums[j]=temp;
    }
```

### 49.字符异位词分组

- 给你一个字符串数组，请你将 **字母异位词** 组合在一起。可以按任意顺序返回结果列表.**字母异位词** 是由重新排列源单词的字母得到的一个新单词，所有源单词中的字母通常恰好只用一次。

- 给字符排序+HashMap

```java
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>>res=new ArrayList<>();
        Map<String,Integer>map=new HashMap<>();
        int index=0;

        for(String str:strs){
            char[] chars=str.toCharArray();
            Arrays.sort(chars);
            String key=new String(chars);

            if(!map.containsKey(key)){
                map.put(key,index++);
                List<String>list=new ArrayList<>();
                list.add(str);
                res.add(list);
            }else {
                res.get(map.get(key)).add(str);
            }
        }
        return res;
    }
```



## ==Queue==

### 09.用两个栈实现队列(剑指)

- 还是用栈吧，不用其他的

```java

/*
两个栈，一个正序放进，一个逆序放进。取的时候从逆序里取
当逆序栈out为空时才倾倒
<>不能是基本类型，可用包装类型
 */

class CQueue {

    private Stack<Integer> in;
    private Stack<Integer> out;
    public CQueue() {
        in=new Stack<>();
        out=new Stack<>();
    }
    
    public void appendTail(int value) {
        in.push(value);
    }
    
    public int deleteHead() {
        if(in.empty()&&out.empty()){
            return -1;
        }
        else if(out.empty()){
            while(!in.empty()){
                out.push(in.pop());
            } 
        }
        return out.pop();
    }
}
```

## ==List==

### 06.从尾到头打印链表(剑指)

- 输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）。
- 用Stack很好地完成逆序输出

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public int[] reversePrint(ListNode head) {
        int count=0;
        //Stack<int> stack=new Stack<>();Integer啊
        Stack<Integer>stack=new Stack<>();

        while(head!=null){
            stack.push(head.val);
            count++;
            head=head.next;
        }
        int []nums=new int[count];


        int i=0;
        while(!stack.empty()){
            nums[i++]=stack.pop();
        }
        return nums;
    }
}
```

- 数组逆序空间复杂度更低

```java
/*
不用Stack更加节省空间，空间复杂度更低
用数组的索引从后往前存就是逆序
*/
class Solution {
    public int[] reversePrint(ListNode head) {
        int count=0;
        ListNode temp=head;
        while(head!=null){
            count++;
            head=head.next;//即使改变head的值也没有关系，引用本身是值传递，不会影响原值
        }
        int []nums=new int[count];
        while(temp!=null){
            nums[--count]=temp.val;//反序
            temp=temp.next;
        }
        return nums;
    }
}
```

### 2.两数相加

- 两个非空的链表，表示两个非负的整数。每位数字逆序存储，每个节点只存储一位数字。将两个数相加，并以相同形式返回一个表示和的链表
- 第一位都是表示个位，直接相加即可
- pre存储上一个结点

```java
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode res=new ListNode();//res.next存储结果。
        int c=0;//进位

        ListNode pre=res;
        //把最短的先处理
        while (l1!=null && l2!=null){
            ListNode node=new ListNode();
            int num=l1.val+l2.val+c;
            if(num<=9){
                node.val=num;
                c=0;
            }else if(num<20){
                node.val=num-10;
                c=1;
            }else {
                node.val=num-20;
                c=2;
            }
            l1=l1.next;
            l2=l2.next;
            pre.next=node;
            pre=node;
        }

        //处理没空的
        ListNode notNUll=l1!=null?l1:l2;
        while (notNUll!=null){
            ListNode node=new ListNode();
            int num=notNUll.val+c;
            if(num<=9){
                node.val=num;
                c=0;
            }else {
                node.val=num-10;
                c=1;
            }
            notNUll=notNUll.next;
            pre.next=node;
            pre=node;
        }

        //处理最后的进位
        if(c!=0){
            ListNode node=new ListNode();
            node.val=c;
            pre.next=node;
        }      
        return res.next;
    }
```

### 21.合并两个有序链表

- 将两个升序链表合并为一个新的 **升序** 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的

```java
public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if(list1==null)
            return list2;
        if(list2==null)
            return list1;
        
        ListNode res=new ListNode();
        ListNode node=res;
        while(list1!=null && list2!=null){
            if(list1.val<list2.val){
                node.next=list1;
                list1=list1.next;
            }else{
                node.next=list2;
                list2=list2.next;
            }
            node=node.next;
        }
        if(list1!=null)
            node.next=list1;
        if(list2!=null)
            node.next=list2;
        return res.next;
   }
```

### 23.合并k个升序链表

- 全取+排序
- ArrayList模拟只有头节点的链表

```java
    public ListNode mergeKLists(ListNode[] lists) {
        if(lists==null){
            return null;
        }
        List<ListNode>listArr=new ArrayList<>();
        //全部取出来，此处只需要一次遍历  ArrayList来模拟
        for(ListNode list:lists){
            while (list!=null){
                listArr.add(list);
                list=list.next;
            }
        }
        if(listArr.size()==0){//listArr.get(0);不能成立
            return null;
        }
        //为空[] 或者[null] 可以排不报错，但是[null,3]之类的不行
        listArr.sort(((o1, o2) ->o1.val-o2.val));
        for (int i=0;i<listArr.size()-1;i++){
            listArr.get(i).next=listArr.get(i+1);
        }
        return listArr.get(0);
    }
```

### 24.两两交换链表

- 给你一个链表，两两交换其中相邻的节点，并返回交换后链表的头节点。你必须在不修改节点内部的值的情况下完成本题（即，只能进行节点交换）。
- ArrayList模拟只有头节点的链表。官方的用指针

```java
    public ListNode swapPairs(ListNode head) {
        if(head==null){
            return null;
        }
        ListNode node=head;// 引用本身应该是值传递，但是可以通过引用改变对象的值，所以这里可以不保存？
        //只存引用，应该不占用多大空间
        List<ListNode>list=new ArrayList<>();
        while (node != null) {
            list.add(node);
            node=node.next;
        }

        //不转也行，直接用list.set(node)  下次不转了
        ListNode[]arr=list.toArray(new ListNode[0]);
        for(int i=1;i<arr.length;i+=2){
            ListNode temp=arr[i];
            arr[i]=arr[i-1];
            arr[i-1]=temp;
        }
        for(int i=0;i<arr.length-1;i++){
            arr[i].next=arr[i+1];
        }
        arr[arr.length-1].next=null;
        return arr[0];
    }
```

### 25.k个一组反转链表

- 和两两那个差不多
- 还是空间复杂度和时间复杂度的问题

```java
     public ListNode reverseKGroup(ListNode head, int k) {
        List<ListNode>list=new ArrayList<>();
        while (head!=null){
            list.add(head);
            head=head.next;
        }

        //转是1ms，不转的话是3ms更慢
        ListNode[]arr=list.toArray(new ListNode[0]);
        for(int i=0;i+k-1<arr.length;i+=k){
            reverse(arr,i,i+k-1);
        }
        for(int i=0;i<arr.length-1;i++){
            arr[i].next=arr[i+1];
        }
        arr[arr.length-1].next=null;
        return arr[0];
    }

    private void reverse(ListNode[]arr,int st,int end){
        //i控制次数，j控制下标
        int j=0;
        for(int i=0;i<(end+1-st)/2;i++){
            ListNode temp=arr[st+j];
            arr[st+j]=arr[end-j];
            arr[end-j]=temp;
            j++;
        }
    }
```

### 61.旋转链表

- 给你一个链表的头节点 `head` ，旋转链表，将链表每个节点向右移动 `k` 个位置
- 连成环

```java
    public ListNode rotateRight(ListNode head, int k) {
        if(head==null){
            return head;
        }
        ListNode head1=head;
        int count=1;
        while (head1.next!=null) {
            count++;
            head1 = head1.next;
        }
        head1.next=head;//连成环

        ListNode res;
        k%=count; //k可能大于count
        for(int i=0;i<count-k-1;i++){
            head=head.next;
        }
        res=head.next;
        head.next=null;
        return res;
    }
```

### 80.删除有序数组的重复项二

- 给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使得出现次数超过两次的元素只出现两次 ，返回删除后数组的新长度。不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成

- 双指针，每个指针处理不同的情况

```java
    public int removeDuplicates(int[] nums) {
        int count=1;
        int insert=1;
        for(int i=1;i<nums.length;i++) {
            if(nums[i]==nums[i-1]){
                count++;
            }else {
                count=1;
            }
            if(count<=2){
                if(i!=insert){
                    nums[insert]=nums[i];
                }
                insert++;
            }
        }
        return insert;
    }
```

### 82.删除排序链表中重复元素二

- 给定一个已排序的链表的头 `head` ， *删除原始链表中所有重复数字的节点，只留下不同的数字* 。返回 *已排序的链表*
- 双指针

```java
    public ListNode deleteDuplicates(ListNode head) {
        if(head==null){
            return null;
        }
        ListNode root=null;
        ListNode tail=null;
        while (head!=null){
            if(head.next!=null && head.val==head.next.val){
                int value=head.val;
                while (head!=null && head.val==value){ //找到下一不重复的
                    head=head.next;
                }
            }else {
                if(tail==null){//若一开始就重复呢对吧
                    root=head;
                    tail=head;
                }else{
                    tail.next=head;
                    tail=tail.next;   //tail也要更新
                }
                head=head.next;
            }
        }
        if(tail!=null){
            tail.next=null;
        }

        return root;
    }
```

### 83.删除排序链表中元素

- 给定一个已排序的链表的头 `head` ， *删除所有重复的元素，使每个元素只出现一次* 。返回 *已排序的链表*
- 遍历

```java
    public ListNode deleteDuplicates(ListNode head) {
        if(head==null){
            return null;
        }

        ListNode root=head;
        ListNode tail=root;
        int preVal=root.val;
        head=head.next;
        while (head!=null){
            if(head.val==preVal){
                while (head!=null && head.val==preVal){
                    preVal=head.val;
                    head=head.next;
                }
            }else {
                tail.next=head;
                tail=tail.next;
                preVal=tail.val;
                head=head.next;
            }
        }
        tail.next=null;
        return root;
    }
```

### 86.分隔链表

- 给你一个链表的头节点 head 和一个特定值 x ，请你对链表进行分隔，使得所有 小于 x 的节点都出现在 大于或等于 x 的节点之前。你应当 保留 两个分区中每个节点的初始相对位置
- 前段后段分别计算

```java
    public ListNode partition(ListNode head, int x) {
        if(head==null){
            return null;
        }
        ListNode mRoot=null;
        ListNode mTail=null;
        ListNode nRoot=null;
        ListNode nTail=null;
        if(head.val<x){
            mRoot=head;
            mTail=head;
        }else {
            nRoot=head;
            nTail=head;
        }
        head=head.next;
        while (head!=null){
            if(head.val<x){
                if(mRoot==null){
                    mRoot=head;
                    mTail=head;
                }else {
                    mTail.next=head;
                    mTail=mTail.next;
                }
            }else {
                if(nRoot==null){
                    nRoot=head;
                    nTail=head;
                }else {
                    nTail.next=head;
                    nTail=nTail.next;
                }
            }
            head=head.next;
        }

        if(mRoot==null){
            return nRoot;
        }
        if(nRoot==null){
            return mRoot;
        }
        mTail.next=nRoot;
        nTail.next=null;
        return mRoot;
    }
```

### 92.反转链表二

- 给你单链表的头指针 head 和两个整数 left 和 right ，其中 left <= right 。请你反转从位置 left 到位置 right 的链表节点，返回 反转后的链表 

```java
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if(left==right){
            return head;
        }
        //没有前节点
        if(left==1){
            ListNode pre = head;
            ListNode tail = head.next;
            for(int i=0;i<right-left;i++){
                ListNode next = tail.next;
                tail.next=pre;
                pre=tail;
                tail=next;
                head.next=next;
            }
            return pre;
        }

        ListNode preNode=head;
        //拿到left的前一个节点
        for(int i=1;i<left-1;i++){
            preNode=preNode.next;
        }
        ListNode node=preNode.next.next;//  (left,right]的节点
        ListNode tailNode=preNode.next; //中间那段的尾，固定不变
        for(int i=0;i<right-left;i++){ //要换的节点个数为right-left
            ListNode preNext=preNode.next;//原来的头
            ListNode nodeNext=node.next; //
            //插入
            preNode.next=node;
            node.next=preNext;
            //续上尾巴
            tailNode.next=nodeNext;
            node=nodeNext;
        }
        return head;
    }
```



## ==Stack==

### 20.有效的括号

- 左括号入栈，右括号出栈一个

```java
public boolean isValid(String s) {
        int n=s.length();
        if(n%2==1)//奇数个
            return false;
        Stack<Character> sta=new Stack<>();
        for(int i=0;i<n;i++){
            char c=s.charAt(i);
            if(c=='('||c=='['||c=='{')
                sta.push(c);
            else{
                if(sta.isEmpty())//没有可以匹配
                    return false;
                char top=sta.pop();
                if(c==']' && top!='[' || c==')' && top!='(' || c=='}' && top!='{' )//不匹配
                    return false;
            }
        }
        if(!sta.isEmpty())//未匹配完
            return false;
        return true;
}
```

### 150.逆波兰表达式求值

- 栈

```java
    public int evalRPN(String[] tokens) {
        Stack<Integer>stack=new Stack<>();
        for (String token : tokens) {
            switch (token) {
                case "+" -> stack.push(stack.pop() + stack.pop());
                case "-" -> stack.push(-stack.pop() + stack.pop());
                case "*" -> stack.push(stack.pop() * stack.pop());
                case "/" -> {
                    int a = stack.pop();
                    int b = stack.pop();
                    stack.push(b / a);
                }
                default -> {
                    stack.push(Integer.parseInt(token));
                }
            }
        }
        return stack.pop();
    }
```

### 71.简化路径

- 给你一个字符串 `path` ，表示指向某一文件或目录的 Unix 风格 **绝对路径** （以 `'/'` 开头），请你将其转化为更加简洁的规范路径

- 栈

```java
    public String simplifyPath(String path) {
        Set<String>set=new HashSet<>();
        set.add(".");
        set.add("..");

        Deque<String>queue=new ArrayDeque<>();//存储各级文件名

        int i=0;
        while (i<path.length()){
            int start=i;
            int count=0;
            for(int j=i;j<path.length();j++){
                if(path.charAt(j)=='/'){
                    start++;
                }else {
                    break;
                }
            }
            for(int j=start;j<path.length();j++){
                if(path.charAt(j)=='/'){
                    break;
                }
                count++;
            }

            if(count!=0){//防止/a//  出现/a/   也就是多了个空串
                String sub=path.substring(start,start+count);//前闭后开
                if(!set.contains(sub)){//串
                    queue.push(sub);
                }else if(sub.equals("..")){
                    if(!queue.isEmpty()){
                        queue.pop();
                    }
                }
            }
            i=start+count+1;
        }

        if(queue.isEmpty()){
            return "/";
        }
        StringBuilder strb=new StringBuilder();
        while (!queue.isEmpty()){
            strb.insert(0,queue.pop());
            strb.insert(0,"/");
        }
        return strb.toString();
    }
```

### 32.最长有效括号

- 给你一个只包含 `'('` 和 `')'` 的字符串，找出最长有效（格式正确且连续）括号子串的长度。要连续的！
- 栈

```java
    //栈
    public int longestValidParentheses(String s) {
        Deque<Character>dequeVal=new ArrayDeque<>();
        Deque<Integer>dequeIndex=new ArrayDeque<>();
        //记录能够组成()的字符的下标
        boolean[]visited=new boolean[s.length()];
        for(int i=0; i<s.length(); i++) {
            char c=s.charAt(i);
            if(dequeVal.isEmpty() || c=='('){
                dequeVal.push(c);
                dequeIndex.push(i);
            } else {
                if(dequeVal.peek()=='('){
                    dequeVal.pop();
                    visited[dequeIndex.pop()]=true;
                    visited[i]=true;
                }else{
                    //不连续了，要隔绝
                    //)也是要入栈，起隔离作用
                    dequeVal.push(c);
                    dequeIndex.push(i);
                }
            }
        }
        //拿到最长连续的true
        int res=0;
        int temp=0;
        for (boolean b : visited) {
            if (b) {
                temp++;
            } else {
                temp = 0;
            }
            res = Math.max(res, temp);
        }
        return res;
    }
```

## ==单调栈==

- 用于有边界的求值

### 42.接雨水

- 给定 `n` 个非负整数表示每个宽度为 `1` 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
- 单调栈     存储单调序列

```java
//想法不错，就是时间复杂度和空间复杂度有点高    
public int trap(int[] height) {
        int n=height.length;
        //栈记录比左边界小的单调递减序列。
        Stack <Integer>stack=new Stack<>();
        int res=0;
        for(int i=0;i<n;i++){
            int h=height[i];
            //height[i]不是递减，考虑先出栈，直到遇到比height[i]大的数
            while (!stack.isEmpty() && h>height[stack.peek()]){
                int top=stack.pop();
                if(stack.isEmpty())//已经为空说明之前只有一个元素在,下面的i入栈是更新左边界
                    break;//左边界上是不可能储水的
                int left=stack.peek();//把前一个作为边界
                int curWidth=i-left-1;
                int curHeight=Math.min(h,height[left])-height[top];//一层层取水
                res+=curWidth*curHeight;
            }
            stack.push(i); //下标入栈
        }
        return res;
    }
```

### 30.包含min函数的栈(剑指)

- min返回最小值。min、push 及 pop 的时间复杂度都是 O(1)
- 辅助栈

```java
/*
返回最小值
一个辅助栈维持一个递减的序列（并不需要所有的元素都在里面）
*/
class MinStack {
    private Stack<Integer>A,B;

    /** initialize your data structure here. */
    public MinStack() {
        A=new Stack<>();
        B=new Stack<>();
    }
    
    public void push(int x) {
        A.push(x);
        if(B.empty()||B.peek()>=x){//要等于，万一重复的一次只可以取出一个
            B.push(x);
        }
    }
    
    public void pop() {
        if(A.pop().equals(B.peek())){//类型时Integer，所以要用equals
            B.pop();
        }
    }
    
    public int top() {
        return A.peek();
    }
    
    public int min() {
        return B.peek();
    }
}
```

### 84.柱状图中最大的矩形

- 给定 *n* 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。求在该柱状图中，能够勾勒出来的矩形的最大面积。（其高度可能为0
- 单调栈（升

```java
    //单调升栈。遇到小的则出栈，出栈的柱子要小于未入栈的。左边界是下一出栈元素下标（因为可能是-1），右边界是未入栈元素下标计算
    public int largestRectangleArea(int[] heights) {
        Deque<Integer>valQueue = new ArrayDeque<>();//值栈
        Deque<Integer>indexQueue = new ArrayDeque<>();//下标栈
        int result=0;
        int start=-1;//上一个为0的位置
        for(int i=0;i<heights.length;i++) {
            if(valQueue.isEmpty() || heights[i]>=valQueue.peek()){//第一个先、升序放进去
                valQueue.push(heights[i]);
                indexQueue.push(i);
                if(heights[i]==0){
                    start=i;
                }
            }else {
                while (heights[i]<valQueue.peek()){ //以栈顶元素为高的正方形一定止步于此
                    int val=valQueue.pop();
                    int index=indexQueue.pop();
                    if(valQueue.isEmpty()){
                        result=Math.max(result,(i-1-start)*val);   //6，7，5，2，4  5出栈时里面就是空了    左边界是上一个0位置
                        break;
                    }else {
                        result=Math.max(result,(i-1-indexQueue.peek())*val);  //左边界是栈顶元素下标
                    }
                }
                //此时栈里，左边没有比heights[i]大且还没出栈的
                valQueue.push(heights[i]);
                indexQueue.push(i);
                if(heights[i]==0){ //最后才更新start，因为上面要用上一个start
                    start=i;
                }
            }
        }
        //处理剩下的
        while (!valQueue.isEmpty()){
            int val=valQueue.pop();
            int index=indexQueue.pop();
            if(valQueue.isEmpty() || valQueue.peek()==0){//剩下的0不用处理了
                result=Math.max(result, (heights.length-1-start)*val);   //6，7，5，2，4  5出栈时里面就是空了。要用start找到上一个为0的位置
                break;
            }else {
                result=Math.max(result,(heights.length-1-indexQueue.peek())*val);
            }
        }
        return result;
    }
```



## ==内部类==

### 73.矩阵置零

- 给定一个 `m x n` 的矩阵，如果一个元素为 **0** ，则将其所在行和列的所有元素都设为 **0** 。请使用原地算法，即输出是对输入的改变
- HashMap  key=i   value=j的话无法存同一行的两个为0的列。用局部内部类+List来存数据

```java
 public void setZeroes(int[][] matrix) {
        class index{//局部内部类
            int line;
            int row;
            index(int i,int j){
                line=i;
                row=j;
            }
        }
        List <index> list=new ArrayList<>();
        for(int i=0;i<matrix.length;i++){//列
            for(int j=0;j<matrix[0].length;j++){
                if(matrix[i][j]==0){
                    list.add(new index(i,j));//标记为0的点
                }
            }
        }
        for(index in:list){
            setZero(matrix,in.line,in.row);//置零
        }

    }

  public void setZero(int [][] matrix,int line,int row){
        for(int k=0;k<matrix[0].length;k++){//行
            matrix[line][k]=0;
        }
        for(int k=0;k<matrix.length;k++){
            matrix[k][row]=0;
        }
    }
```

## ==数组==

### 387. 字符串中的第一个唯一字符

- 给定一个字符串 `s` ，找到 它的第一个不重复的字符，并返回它的索引* 。如果不存在，则返回 `-1` 。
  - s 只包含小写字母
- HashMap存储出现次数
  - 33ms

```java
public class Solution {
    public int firstUniqChar(String s) {
        HashMap<Character,Integer> map=new HashMap<>();
        for(int i=0;i<s.length();i++){
            char c=s.charAt(i);
            if(!map.containsKey(c)){
                map.put(c,1);
            }else {
                map.put(c,map.get(c)+1);
            }
        }
        for (int i=0;i<s.length();i++){
            if(map.get(s.charAt(i))==1){
                return i;
            }
        }
        return -1;
    }
```

- int数组存储
  - 6ms，比HashMap快很多
  - 下标i与数据有一一对应关系，值[i]记录出现次数

```java
public int firstUniqChar(String s) {
    	//目前只适用于字母
       	int[] arr = new int[26];//26个的话只适合都是小写或大写。要所有字母的话就声明2个
        int n = s.length();
        for (int i = 0; i < n; i++) {
            arr[s.charAt(i)-'a']++ ;
        }
        for (int i = 0; i < n; i++) {
            if (arr[s.charAt(i)-'a'] == 1) {
                return i;
            }
        }
        return -1;
}
```

- String的方法（还不错
  - 25ms

```java
public int firstUniqChar(String s) {
        for (int i = 0; i < s.length(); i++) {
            //第一次出现和最后一次出现位置一样
            if (s.indexOf(s.charAt(i)) == s.lastIndexOf(s.charAt(i))) {
                return i;
            }
        }
        return -1;
}
```

### 383. 赎金信

- 给你两个字符串：`ransomNote` 和 `magazine` ，判断 `ransomNote` 能不能由 `magazine` 里面的字符构成。
  - `ransomNote` 和 `magazine` 由小写英文字母组成
  - `magazine` 中的每个字符只能在 `ransomNote` 中使用一次
- 用int[26]存char和int的映射关系

```java
    public boolean canConstruct(String ransomNote, String magazine) {
        int []nums=new int[26];
        for(int i=0;i<ransomNote.length();i++){
            nums[ransomNote.charAt(i)-'a']++;
        }
        for(int i=0;i<magazine.length();i++){
            nums[magazine.charAt(i)-'a']--;
        }

        for(int i=0;i<26;i++){
            if(nums[i]>0){
                return false;
            }
        }
        return true;
  }
```

## ==二叉树==

### 94.二叉树的中序遍历

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
            pre=node;   //访问过的
            node=node.right;//走一步右
        }
        return res;
    }
```

- 递归
  -  可以自己再定义一个递归函数

```java
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res=new ArrayList<>();
        getList(root,res);
        return res;
    }

    public void getList(TreeNode root,List<Integer>list){
        if(root==null){  //临界条件，当前为null就行了
            return;
        }
        //有什么操作就执行什么，注意顺序
        getList(root.left,list);
        list.add(root.val);
        getList(root.right,list);
    }
```

### 95.不同的二叉搜索树二（没懂）

- 回溯
  - 递归方法有返回值。思路清晰，代码不用写多少
- 至今不太懂

```java
    public List<TreeNode> generateTrees(int n) {
        if(n==0){
            return new ArrayList<TreeNode>();
        }
        return generateTrees(1,n);//数字是1到n
    }

    public List<TreeNode> generateTrees(int st,int end){
        List<TreeNode>allTrees=new ArrayList<>();
        if(st>end){
            allTrees.add(null);   //为什么要add(null)?
            return allTrees;
        }
        //枚举所有可行根节点
        for(int i=st;i<=end;i++){
            //生成左右，因为已经排好序了，左比右小
            List<TreeNode> leftTrees=generateTrees(st,i-1);
            List<TreeNode> rightTrees=generateTrees(i+1,end);
            //以i作为根节点合并
            for(TreeNode left:leftTrees){
                for(TreeNode right:rightTrees){
                    TreeNode node=new TreeNode(i);
                    node.left=left;
                    node.right=right;
                    allTrees.add(node);  
                }
            }
        }
        return allTrees;
    }
```

### 144.二叉树的前序遍历

- 递归

```java
    List<Integer> res = new ArrayList<>();
    public List<Integer> preorderTraversal(TreeNode root) {
        if (root != null) {
            res.add(root.val);
            preorderTraversal(root.left);
            preorderTraversal(root.right);
        }
        return res;
    }
```

### 145.二叉树的后序遍历

- 递归

```java
    List<Integer>res=new ArrayList<>();
    public List<Integer> postorderTraversal(TreeNode root) {
        if(root!=null){
            postorderTraversal(root.left);
            postorderTraversal(root.right);
            res.add(root.val);
        }
        return res;
    }
```

### 102.二叉树的层序遍历

- 2个栈，一个当前层，一个下一层

```java
    List<List<Integer>>res=new ArrayList<>();
    Deque<TreeNode>deque1=new ArrayDeque<>();
    Deque<TreeNode>deque2=new ArrayDeque<>();
    public List<List<Integer>> levelOrder(TreeNode root) {
         if(root!=null){
             deque1.addLast(root);
         }
        if(!deque1.isEmpty() || !deque2.isEmpty()){
            getRes();
        }
         
        return res;
    }
    
    private void getRes(){

        List<Integer>list=new ArrayList<>();
        Deque<TreeNode>empty=deque1.isEmpty() ? deque1:deque2;
        Deque<TreeNode>noEmpty=deque1.isEmpty() ?deque2:deque1;
        
        while (!noEmpty.isEmpty()){
            list.add(noEmpty.getFirst().val);
            if(noEmpty.getFirst().left!=null){
                empty.add(noEmpty.getFirst().left);
            }
            if(noEmpty.getFirst().right!=null){
                empty.add(noEmpty.getFirst().right);
            }
            noEmpty.removeFirst();
        }
        res.add(list);

        if(!deque1.isEmpty() || !deque2.isEmpty()){
            getRes();
        }
    }
```

### 107.二叉树的层序遍历二

- 从下往上遍历
- res.add(0,list);//搞定

```java
    List<List<Integer>>res=new ArrayList<>();
    Deque<TreeNode>deque1=new ArrayDeque<>();
    Deque<TreeNode>deque2=new ArrayDeque<>();
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        if(root!=null){
            deque1.addLast(root);
        }
        if(!deque1.isEmpty() || !deque2.isEmpty()){
            getRes();
        }

        return res;
    }

    private void getRes(){

        List<Integer>list=new ArrayList<>();
        Deque<TreeNode>empty=deque1.isEmpty() ? deque1:deque2;
        Deque<TreeNode>noEmpty=deque1.isEmpty() ?deque2:deque1;

        while (!noEmpty.isEmpty()){
            list.add(noEmpty.getFirst().val);
            if(noEmpty.getFirst().left!=null){
                empty.add(noEmpty.getFirst().left);
            }
            if(noEmpty.getFirst().right!=null){
                empty.add(noEmpty.getFirst().right);
            }
            noEmpty.removeFirst();
        }
        res.add(0,list);

        if(!deque1.isEmpty() || !deque2.isEmpty()){
            getRes();
        }
    }
```

### 101.对称二叉树，666

- 给你一个二叉树的根节点 `root` ， 检查它是否轴对称

```java
    //镜像递归。神仙的简单题
    public boolean isSymmetric(TreeNode root) {
        return check(root,root);
    }

    private boolean check(TreeNode left,TreeNode right) {
        if(left==null && right==null){
            return true;
        }
        if(left==null || right==null){
            return false;
        }
        return left.val== right.val && check(left.left,right.right) && check(left.right,right.left);
    }
```

### 103.二叉树的锯齿形层序遍历

- 相隔的2层一层左到右，一层右到左
- 其实用一个队列就可以了
- boolean isRight=true;  //记录方向

```java
    List<List<Integer>>res=new ArrayList<>();
    Deque<TreeNode>deque=new ArrayDeque<>();
    boolean isRight=true;
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if(root!=null){
            deque.add(root);
            getRes();
        }
        return res;
    }
    private void getRes(){
        List<Integer>list=new ArrayList<>();
        int i=0;
        int size=deque.size();
        while (i++<size){
            TreeNode node=deque.getFirst();
            if(isRight){//方向
                list.add(node.val);
            }else {
                list.add(0,node.val);
            }

            if(node.left!=null){
                deque.add(node.left);
            }
            if (node.right!=null){
                deque.add(node.right);
            }
            deque.removeFirst();
        }
        isRight=!isRight;
        res.add(list);
        if(!deque.isEmpty()){
            getRes();
        }
    }
```

### 104.二叉树的最大深度

- 深度优先

```java
    public int maxDepth(TreeNode root) {
        if(root==null){
            return 0;
        }
        return Math.max(maxDepth(root.left),maxDepth(root.right))+1;
    }
```

- 广度优先（层序遍历）

```java
    int count = 0;
    Deque<TreeNode>deque=new ArrayDeque<>();
    public int maxDepth(TreeNode root) {
        if (root != null) {
            deque.add(root);
            getRes();
        }
        return count;
    }
    private void getRes(){
        count++;
        int i=0;
        int size=deque.size();
        while (i++<size){
            TreeNode node=deque.getFirst();
            if(node.left!=null){
                deque.add(node.left);
            }
            if (node.right!=null){
                deque.add(node.right);
            }
            deque.removeFirst();
        }
        if(!deque.isEmpty()){
            getRes();
        }
    }
```

### 114.二叉树展开为链表

- 要求前序遍历。

```java
    List<TreeNode>list=new ArrayList<>();
    public void flatten(TreeNode root) {
        if(root==null){
            return;
        }
        getRes(root);
        for(int i=0;i<list.size()-1;i++) {
            list.get(i).left=null;
            list.get(i).right=list.get(i+1);
        }
        list.get(list.size()-1).left=null;
        list.get(list.size()-1).right=null;
    }

    private void getRes(TreeNode root){
        if(root!=null){
            list.add(root);
            getRes(root.left);
            getRes(root.right);
        }
    }
```

### 111.二叉树的最小深度

- 广度优先

```java
    Deque<TreeNode> now=new ArrayDeque<>();//当前层
    Deque<TreeNode> next=new ArrayDeque<>();//下一层
    int depth;
    public int minDepth(TreeNode root) {
        if(root!=null){
            now.add(root);
        }

        getResult();
        return depth;
    }

    public void getResult(){
        if(!now.isEmpty()){
            depth++;
            while (!now.isEmpty()){
                TreeNode node=now.getFirst();
                if(node.left==null && node.right==null){
                    return;
                }
                if(node.left!=null){
                    next.add(node.left);
                }
                if(node.right!=null){
                    next.add(node.right);
                }
                now.removeFirst();
            }
            //交换当前层和下一层
            Deque<TreeNode>temp=now;
            now=next;
            next=temp;
            getResult();
        }
    }
```

### 104.从前序与中序遍历构造二叉树

- 构造二叉树的这几题，都是从根节点开始

- 递归（官方
  - 前序确定根节点，中序确定子树边界

```java
    private Map<Integer, Integer>indexMap;
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        indexMap=new HashMap<>();
        int n=preorder.length;
        for(int i=0;i<n;i++){
            indexMap.put(inorder[i],i);
        }
        return getRootVal(preorder,inorder,0,n-1,0,n-1);
    }

    //node父节点，p,q前序的，i，j中序
    private TreeNode getRootVal(int []pre,int []in,int p,int q,int i,int j){
        if(i>j){
            return null;
        }
        //构建根节点
        TreeNode root=new TreeNode(pre[p]);
        //获取根节点在中序的位置
        int index=indexMap.get(pre[p]);
        //左子树节点数
        int left_count=index-i;
        //前序向前走left_count步，对应中序的左子树
        root.left=getRootVal(pre,in,p+1,p+left_count,i,index-1);
        //剩下的是右子树
        root.right=getRootVal(pre,in,p+left_count+1,q,index+1,j);
        return root;
    }
```

### 105.从中序与后序遍历构造二叉树

- 递归(官方)
  - 后序确定根节点，中序确定边界

```java
    int post_idx;
    int[] postorder;
    int[] inorder;
    Map<Integer, Integer> idx_map = new HashMap<Integer, Integer>();

    //n_left,in_right是为了确认子树边界
    public TreeNode helper(int in_left, int in_right) {
        // 如果这里没有节点构造二叉树了，就结束
        if (in_left > in_right) {
            return null;
        }

        // 选择 post_idx 位置的元素作为当前子树根节点
        int root_val = postorder[post_idx];
        TreeNode root = new TreeNode(root_val);

        // 根据 root 所在位置分成左右两棵子树
        int index = idx_map.get(root_val);

        // 下标减一
        post_idx--;
        // 构造右子树。右子树每个节点都构造完后才轮到左子树，所以post_idx--;没毛病
        root.right = helper(index + 1, in_right);
        // 构造左子树
        root.left = helper(in_left, index - 1);
        return root;
    }

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        this.postorder = postorder;
        this.inorder = inorder;
        // 从后序遍历的最后一个元素开始
        post_idx = postorder.length - 1;

        // 建立（元素，下标）键值对的哈希表
        int idx = 0;
        for (Integer val : inorder) {
            idx_map.put(val, idx++);
        }

        return helper(0, inorder.length - 1);
    }
```

### 108.将有序数组转化为二叉搜索树

- 先根节点
- 递归

```java
    public TreeNode sortedArrayToBST(int[] nums) {
        return getRoot(nums,0,nums.length-1);
    }
    
    public TreeNode getRoot(int []nums,int i,int j){
        if(i>j){
            return null;
        }
        int middle=(i+j)>>1;
        TreeNode root=new TreeNode(nums[middle]);
        root.left=getRoot(nums,i,middle-1);
        root.right=getRoot(nums,middle+1,j);
        return root;
    }
```

### 109.有序链表转为二叉搜索树

- 转为数组（时间有点慢

```java
    public TreeNode sortedListToBST(ListNode head) {
        List<Integer>list=new ArrayList<>();
        while (head!=null){
            list.add(head.val);
            head=head.next;
        }
        return getRoot(list,0,list.size()-1);
    }

    public TreeNode getRoot(List<Integer>list,int i,int j){
        if(i>j){
            return null;
        }
        int middle=(i+j)>>1;
        TreeNode root=new TreeNode(list.get(middle));
        root.left=getRoot(list,i,middle-1);
        root.right=getRoot(list,middle+1,j);
        return root;
    }
```

- 快慢指针找中间根节点（官方

```java
class Solution {
    public TreeNode sortedListToBST(ListNode head) {
        return buildTree(head, null);
    }

    public TreeNode buildTree(ListNode left, ListNode right) {
        if (left == right) {
            return null;
        }
        ListNode mid = getMedian(left, right);
        TreeNode root = new TreeNode(mid.val);
        root.left = buildTree(left, mid);
        root.right = buildTree(mid.next, right);
        return root;
    }

    public ListNode getMedian(ListNode left, ListNode right) {
        ListNode fast = left;
        ListNode slow = left;
        while (fast != right && fast.next != right) {
            fast = fast.next;
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }
}
```

### 110.平衡二叉树

- 左右子树高度差不超过1
  - 对所有节点进行深度优先。（两个深度优先遍历

```java
    public boolean isBalanced(TreeNode root) {
        if(root==null){
            return true;
        }
        return Math.abs(maxDepth(root.left)-maxDepth(root.right))<=1
                && isBalanced(root.left) && isBalanced(root.right);
    }

    public int maxDepth(TreeNode root) {
        if(root==null){
            return 0;
        }
        return Math.max(maxDepth(root.left),maxDepth(root.right))+1;
    }
```

### 112.路径总和

- 根节点到叶子节点路径上的值的总和是否等于目标值
  - 递归

```java
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if(root==null){
            return false;
        }
        if(targetSum==root.val && root.left==null && root.right==null){
            return true;
        }

        return hasPathSum(root.left,targetSum-root.val)
                || hasPathSum(root.right,targetSum-root.val);

    }
```

### 113.路径总和二

- 记录112所有满足的路径

```java
    List<Integer>list=new ArrayList<>();
    List<List<Integer>>res=new ArrayList<>();
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        if(root==null){
            return res;
        }
        list.add(root.val);
        if(root.val==targetSum && root.left==null && root.right==null){
            res.add(new ArrayList<>(list));//要新建
        }
        pathSum(root.left,targetSum-root.val);
        pathSum(root.right,targetSum-root.val);
        list.remove(list.size()-1);
        return res;
    }
```

### 100.相同的树

- 判断两棵树结构和值是否相同
- dfs

```java
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p==null && q==null){
            return true;
        }else if(p!=null && q!=null ){
            if(p.val!=q.val){
                return false;
            }else {
                return isSameTree(p.left,q.left) && isSameTree(p.right,q.right);
            }
        }else {
            return false;
        }
    }

```

### 124.二叉树中的最大路径和

- 路径 被定义为一条从树中任意节点出发，沿父节点-子节点连接，达到任意节点的序列。同一个节点在一条路径序列中 至多出现一次 。该路径 至少包含一个 节点，且不一定经过根节点。**路径和** 是路径中各节点值的总和。
- 返回值中左右节点只能取一个（向上返回，当前结果不作为根节点），更新结果时则左右都要算（当前节点作为根节点）

```java
    int maxVal;
    //同一个节点在一条路径序列中 至多出现一次
    public int maxPathSum(TreeNode root) {
        maxVal = root.val;
        dfs(root);
        return maxVal;
    }

    //返回值和要取的值有所不同的dfs
    public int dfs(TreeNode node) {
        if(node==null){
            return 0;
        }

        int leftVal=Math.max(dfs(node.left),0);
        int rightVal=Math.max(dfs(node.right),0);
        maxVal=Math.max(maxVal,leftVal+rightVal+node.val);

        //只能加左或者右一个，否则可能路径重复
        return node.val+Math.max(leftVal,rightVal);
    }
```

### 116.填充每个节点的下一个右侧节点指针

- 给定一个 **完美二叉树** ，填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 `NULL`
- 层序思想

```java
    //上一行本身就用指针完成了一个层序遍历，所以不需要额外的n空间的栈存储
    public Node connect(Node root) {
        if(root==null || root.left==null){
            return root;
        }
        Node firstNode=root.left;
        Node head=root;
        while (root!=null){
            root.left.next=root.right;
            if(root.next!=null){
                root.right.next=root.next.left;
            }
            root=root.next;
        }
        connect(firstNode);
        //所以为什么要返回呢  ，就当拿到一个原引用？但是引用本身不是值传递吗
        return head;
    }
```

### 117.填充每个节点的下一个右侧节点指针

- 给定一个 **二叉树** ，填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 `NULL`
- 层序思想+多2个变量保存一些结果而已

```java
    public Node connect(Node root) {
        if(root==null){
            return null;
        }
        Node firstNode=null;
        Node head =root;
        //拿到下一层的左节点
        while (root!=null){
            if(root.left!=null){
                firstNode=root.left;
                break;
            }
            if(root.right!=null){
                firstNode=root.right;
                break;
            }
            root=root.next;
        }
        //并没有下一层
        if(firstNode==null){
            return head;
        }

        //初始化一个前节点
        Node pre=firstNode;
        if(root.left!=null && root.right!=null){
            root.left.next=root.right;
            pre=root.right;
        }
        root=root.next;
        //前节点指向当前非空的，并更新前节点
        while (root!=null){
            if(root.left!=null){
                pre.next=root.left;
                pre=root.left;
            }
            if(root.right!=null){
                pre.next=root.right;
                pre=root.right;
            }
            root=root.next;
        }
        connect(firstNode);
        return head;
    }
```



# 算法

## ==动态规划==

- 既然用了dp，就不要想得那么复杂

### 53. 最大子数组和

- 给你一个整数数组 `nums` ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
- 用 f(i)代表以第 i个数结尾的「连续子数组的最大和]。
- dp

```java
 public int maxSubArray(int[] nums) {
      int pre=0;//第-1个为0
      int maxVal=nums[0];
     
      for(int num:nums){
          //要么以num结尾，要么以num开始
          pre=Math.max(pre+num,num);
          maxVal=Math.max(pre,maxVal);
      }
      return maxVal;
    }
```

### 62.不同路径

- 向下或向右。m*n格，有多少条不同路径到达右下角
- 动态规划       填二维表

```java
    public int uniquePaths(int m, int n) {
        int [][]dp=new int[m][n];
        for(int i=0;i<n;i++)
            dp[0][i]=1;
        for(int i=0;i<m;i++)
            dp[i][0]=1;
        for(int i=1;i<m;i++){
            for(int j=1;j<n;j++)
                dp[i][j]=dp[i-1][j]+1+dp[i][j-1];//填二维表
        }
        return dp[m-1][n-1];
    }
```

- 数学排列组合

### 64.最小路径和

- 给定一个包含非负整数的 `*m* x *n*` 网格 `grid` ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。

  **说明：**每次只能向下或者向右移动一步

```java
//dp     和62一样，只不过权重不再是1
public int minPathSum(int[][] grid) {
        int n=grid.length;
        int m=grid[0].length;
        int [][]dp=new int[n][m];
        dp[0][0]=grid[0][0];
        for(int i=1;i<m;i++)
            dp[0][i]=grid[0][i]+dp[0][i-1];
        for(int i=1;i<n;i++)
            dp[i][0]=grid[i][0]+dp[i-1][0];

        for(int i=1;i<n;i++){
            for(int j=1;j<m;j++)
                dp[i][j]=grid[i][j]+Math.min(dp[i-1][j],dp[i][j-1]);
        }
        return dp[n-1][m-1];
    }
```

### 70.爬楼梯

- 假设你正在爬楼梯。需要 `n` 阶你才能到达楼顶。每次你可以爬 `1` 或 `2` 个台阶。你有多少种不同的方法可以爬到楼顶呢？

```java
//dp   
public int climbStairs(int n) {
        if(n==1)
            return 1;
        if(n==2)
            return 2;
        int []dp=new int[n];
        dp[0]=1;
        dp[1]=2;
        for(int i=2;i<n;i++)
            dp[i]=dp[i-1]+dp[i-2];
        return dp[n-1];
    }
```

### 85.最大矩形

- 给定一个仅包含 `0` 和 `1` 、大小为 `rows x cols` 的二维二进制矩阵，找出只包含 `1` 的最大矩形，并返回其面积。

```java
//dp填2张二维表。当前节点的最大宽度和高度
public int maximalRectangle(char[][] matrix) {
        int res=0;
        int n=matrix.length;
        int m=matrix[0].length;
        int [][]dpW=new int[n][m];//最大宽
        int [][]dpH=new int[n][m];
        
        for(int i=0;i<n;i++){
            dpW[i][0]=matrix[i][0]=='1'?1:0;//dp都要有初始值。
            for(int j=1;j<m;j++){
                if(matrix[i][j]=='1'){
                    dpW[i][j]=1+dpW[i][j-1];//连续的1则累加
                }
            }
        }
        for(int j=0;j<m;j++){
            dpH[0][j]=matrix[0][j]=='1'?1:0;
            for(int i=1;i<n;i++){
                if(matrix[i][j]=='1'){
                    dpH[i][j]=1+dpH[i-1][j];
                }
            }
        }

        for(int i=0;i<n;i++){//以matrix[i][j]作为右下角的矩形
            for(int j=0;j<m;j++){
                if(dpW[i][j]!=0) {
                    int x = j;
                    int minH = dpH[i][j];
                    while (x>=0 && dpW[i][x] != 0) {
                        if (dpH[i][x] < minH)   //不断更新最小高度，也就是边界高度
                            minH = dpH[i][x];
                        int area = minH * (j - x + 1);//宽度*最小高度
                        if (area > res)
                            res = area;
                        x--;     //宽向左延伸 
                    }
                }
            }
        }
        return res;
    }
```

- 单调栈
  - 写完84题再补充

### 63.不同路径二

- m*n 只能向右、向下，并且有障碍物在任何位置
- 二维数组上dp不是递归。。

```java
    int m;
    int n;
    int [][] dp;
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        m=obstacleGrid.length;
        n=obstacleGrid[0].length;
        dp=new int[m][n];

        //初始化第一行第一列
        for(int i=0;i<m;i++){
            if(obstacleGrid[i][0]==1){//有石头挡住后面的都默认值为0
                break;
            }
            dp[i][0]=1;
        }
        for(int i=0;i<n;i++){
            if(obstacleGrid[0][i]==1){
                break;
            }
            dp[0][i]=1;
        }

        //dp是遍历不是递归
        for(int i=1;i<m;i++){
            for(int j=1;j<n;j++){
                getRes(obstacleGrid,i,j);
            }
        }
        return dp[m-1][n-1]==-1?0:dp[m-1][n-1];
    }

    //避雷。这不是动态规划，一开始写的那个是递归啊。。。
    public void getRes(int [][] obstaclesGrid,int x,int y){
        if(x>=m || y>=n){
            return;
        }
        if(obstaclesGrid[x][y]==1){
            dp[x][y]=-1;//标记下
        }else {
            if(x>0 && dp[x-1][y]!=-1){
                dp[x][y]+=dp[x-1][y];
            }
            if(y>0 && dp[x][y-1]!=-1){
                dp[x][y]+=dp[x][y-1];
            }
        }
    }
```

### 123.买股票的最佳时机三

- 给定一个数组，它的第 `i` 个元素是一支给定的股票在第 `i` 天的价格。设计一个算法来计算你所能获取的最大利润。你最多可以完成 **两笔** 交易

- 模拟最后一天状态

```java
    //此处dp为模拟最后一天状态。
    public int maxProfit(int[] prices) {
        //5种情况。还有一种当天不动，不会产生利润变化，不作考虑
        //dp初始值
        int buy1=-prices[0];   //第一天买   。没有利润，亦可以理解为负数
        int sell1=0; //第一天买后，再卖
        int buy2=-prices[0]; //第一天买了后，再卖，再买
        int sell2=0; //第一天买了后，再卖，再买，再卖
        for(int i=1;i<prices.length;i++){
            buy1=Math.max(buy1,-prices[i]);
            sell1=Math.max(sell1,prices[i]+buy1);
            buy2=Math.max(buy2,sell1-prices[i]);
            sell2=Math.max(sell2,prices[i]+buy2);
        }
        return sell2;
    }
```

### 10.正则表达式匹配

- 给你一个字符串 `s` 和一个字符规律 `p`，请你来实现一个支持 `'.'` 和 `'*'` 的正则表达式匹配
  - `'.'` 匹配任意单个字符
  - `'*'` 匹配零个或多个前面的那一个元素  （能把前面那个变消失。。
  - s、p的长度至少为1
- dp

```java
    public boolean isMatch(String s, String p) {
        //p作为dp子序列
        boolean[][] dp = new boolean[p.length() + 1][s.length() + 1];
        dp[0][0] = true;//增加一行初始值，方便处理。（常为空""

        for (int i = 1; i <= p.length(); i++) {
            boolean flag = false;
            for (int j = 1; j <= s.length(); j++) {
                //p.charAt(i-1)  可为字母 . *
                // 这三种都要用到dp[i-1][j-1]
                if (p.charAt(i - 1) >= 'a' && p.charAt(i - 1) <= 'z') {
                    dp[i][j] = p.charAt(i - 1) == s.charAt(j - 1) && dp[i - 1][j - 1];
                } else if (p.charAt(i - 1) == '.') {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    //*   *不会在开头  也不会出现连续*
                    // 为*时要判断前一个，
                    //若是字母，则dp[i-1][j-1]为true时  且s.charAt(j)==s.charAt(j-1)   则dp[i][j]为true  继续匹配，相等则也为true

                    //*还可以让前面匹配的字母消失    如aab  c*a*b   应该返回true
                    //匹配-1个，0个
                    if (!flag) {
                        //*可能匹配断断续续的，但是合并一次就够了，否则可能会把dp[i][l]为true的变为false
                        //运行效率大大提升了。。每次合并dp[i-2][l] || dp[i-1][l] || dp[i][l] 要108ms
                        //而只合并一次只执行了2ms
                        for (int l = 0; l < dp[0].length; l++) {
                            //dp[i-2][l]  表示拿走前一个字母，也就是-1个
                            ///dp[i-1][l]  表示不拿走也不增加，也就是0个
                            dp[i][l] = dp[i - 2][l] || dp[i - 1][l];
                        }
                        flag = true;
                    }

                    //若是.  dp[i-1][j-1]为true时，dp[i][j]到dp[i][结尾]都是true;
                    if (p.charAt(i - 2) == '.') {
                        if (dp[i - 1][j - 1]) { //从头顶下来直接匹配
                            for (int k = j; k <= s.length(); k++) {
                                dp[i][k] = true;
                            }
                            break;
                        }
                    } else {
                        // 字母，增加1个 ...n个
                        if (dp[i - 1][j - 1]) {
                            if (s.charAt(j - 1) == s.charAt(j - 2)) {
                                for (int k = j; k <= s.length(); k++) {
                                    //匹配连续且相同的
                                    if (s.charAt(k - 1) != s.charAt(j - 2)) {
                                        //可能还没匹配完，不知道。之后结束后j会++
                                        j = k;
                                        break;
                                    }
                                    dp[i][k] = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return dp[p.length()][s.length()];

    }
//        for(int i=0;i<dp.length;i++){
//            for(int j=0;j<dp[0].length;j++){
//                System.out.print(dp[i][j]+" ");
//            }
//            System.out.println();
//        }
    //sol.isMatch("abbcdde","ab*c.*e")
//        true false false false false false false false
//        false true false false false false false false
//        false false true false false false false false
//        false false false true false false false false
//        false false false false true false false false
//        false false false false false true false false
//        false false false false true  true  true  true
//        false false false false false false false true
```

### 44.通配符匹配

- 给定一个字符串 (`s`) 和一个字符模式 (`p`) ，实现一个支持 `'?'` 和 `'*'` 的通配符匹配
  - '?' 可以匹配任何单个字符。
  - '*' 可以匹配任意字符串（包括空字符串）
  - s  p 都可能为空串
- dp

```java
    public boolean isMatch(String s, String p) {
        boolean[][]dp=new boolean[p.length()+1][s.length()+1];
        dp[0][0]=true;

        //s=""   p="***"等   因为s可能为空，所以这行要计算
        for(int i=1;i<=p.length();i++){
            if(p.charAt(i-1)=='*'){
                dp[i][0]=true;
            }else {
                break;
            }
        }

        for(int i=1;i<=p.length();i++){
            for(int j=1;j<=s.length();j++){
                if(p.charAt(i-1)>='a' && p.charAt(i-1)<='z'){
                    dp[i][j]=p.charAt(i-1)==s.charAt(j-1) && dp[i-1][j-1];
                }else if(p.charAt(i-1)=='?'){
                    dp[i][j]=dp[i-1][j-1];
                }else {
                    //* 不会消除前一个字母  dp[i-1]行的true的下方向右全为true
                    if(dp[i-1][j-1]){
                        for(int k=j-1;k<=s.length();k++){
                            dp[i][k]=true;
                        }
                        break;
                    }
                    //最后一个没有左上
                    if(j==s.length()){
                        dp[i][j]=dp[i-1][j];
                    }
                }

            }
        }
        return dp[p.length()][s.length()];
    }
```

### 72.编辑距离

- 给你两个单词 `word1` 和 `word2`， *请返回将 `word1` 转换成 `word2` 所使用的最少操作数* 
  - 可以对word1进行插入、删除、替换字符
- dp

```java
    public int minDistance(String word1, String word2) {
        /**
         *
         * 不匹配word1[i-1]!=word2[j-1]。以下->表示数据处理的流动方向
         * 第一种情况
         * dp[i][j]->dp[i-1][j]     把word1[i-1]删除了，就能回到上一行匹配的dp[i-1][j]状态
         * 或者这样想 dp[i-1][j]->dp[i][j]   dp[i-1][j]已经匹配了，此时多了word1[i-1],那么把它删除即可
         * 第二种
         * word2[j-1]不能删除，结果要在第j列，所以
         * dp[i][j-1]->dp[i][j]     word1[i] 已经和word2[j-1] 匹配了，此时又多了一个字符c=word2.charAt(j)  那么增加word1增加一个字符c即可匹配
         * 第三种
         * dp[i-1][j-1]-> dp[i][j]   word1和word2都增加了一个，直接把word1[i-1]替换成word2[j-1]是最快的，比先删后增和先增后删快
         *
         * 匹配word1[i-1]==word2[j-1]
         * dp[i-1][j-1]-> dp[i][j]     word1和word2都新增一个相同的，所以不用替换，也比先删后增和先增后删快
         */
        int [][]dp=new int[word1.length()+1][word2.length()+1];
        for(int j=0;j<dp[0].length;j++){
            dp[0][j]=j;
        }
        for(int i=0;i< dp.length;i++){
            dp[i][0]=i;
        }

        for(int i=1;i<=word1.length();i++){
            for(int j=1;j<=word2.length();j++){
                if(word1.charAt(i-1)==word2.charAt(j-1)){
                    dp[i][j]=dp[i-1][j-1];
                }else {
                    dp[i][j]=Math.min(dp[i-1][j],dp[i][j-1]);
                    dp[i][j]=Math.min(dp[i][j],dp[i-1][j-1])+1;
                }
            }
        }
        return dp[word1.length()][word2.length()];
    }
```

### 91.解码方法

- 给你一个只含数字的 **非空** 字符串 `s` ，请计算并返回 **解码** 方法的 **总数**

  - ```
    输入：s = "226"
    输出：3
    解释：它可以解码为 "BZ" (2 26), "VF" (22 6), 或者 "BBF" (2 2 6) 。
    ```

- dp

```java
    public int numDecodings(String s) {
        //有前导0
        if (s.charAt(0) == '0') {
            return 0;
        }
        int[] dp = new int[s.length()];
        //处理0，1 位置
        dp[0]=1;
        if(s.length()>1){
            if(s.charAt(1)== '0'){
                if(s.charAt(0)>'2'){
                    return 0;
                }
                dp[1]=1;
            }else {
                if(s.charAt(0)=='1' || s.charAt(0)=='2' && s.charAt(1)<='6'){
                    dp[1]=2;
                }else {
                    dp[1]=1;
                }
            }

        }


        for (int i = 2; i < s.length(); i++) {
            if (s.charAt(i) == '0') {
                //不能跟前一个匹配，因为0也不能单独存在，所以直接结束
                if (s.charAt(i - 1) == '1' || s.charAt(i - 1) == '2') {
                    dp[i] = dp[i - 2] ;
                }else {
                    return 0;
                }
            } else {
                if (s.charAt(i - 1) == '1' || s.charAt(i - 1) == '2' && s.charAt(i) <= '6') {
                    dp[i] = dp[i - 1] + dp[i-2];
                } else {
                    dp[i] = dp[i - 1];
                }
            }
        }
        return dp[s.length() - 1];
    }
```

### 97.交错字符串

- 给定三个字符串 `s1`、`s2`、`s3`，请你帮忙验证 `s3` 是否是由 `s1` 和 `s2` **交错** 组成的。//s1,s2,s3都不为null
- dp

```java
    public boolean isInterleave(String s1, String s2, String s3) {


        if(s1.length()+s2.length()!=s3.length()){
            return false;
        }

        boolean [][]dp=new boolean[s1.length()+1][s2.length()+1];
        //初始化
        dp[0][0]=true;
        for(int i=1;i<=s1.length();i++){
            if(s1.charAt(i-1)!=s3.charAt(i-1)){
                break;
            }
            dp[i][0]=true;
        }
        for(int j=1;j<=s2.length();j++){
            if(s2.charAt(j-1)!=s3.charAt(j-1)){
                break;
            }
            dp[0][j]=true;
        }

        //dp
        for(int i=1;i<=s1.length();i++){
            for(int j=1;j<=s2.length();j++){
                dp[i][j]=(dp[i-1][j] && s1.charAt(i-1)==s3.charAt(i+j-1) || (dp[i][j-1] && s2.charAt(j-1) ==s3.charAt(i+j-1)));
            }
        }
        return dp[s1.length()][s2.length()];
    }
```

- dfs，这个感觉没错，但是超时了
  - 能dp就别dfs

```java
    boolean res;
    public boolean isInterleave(String s1, String s2, String s3) {

        if(s3==null){
            return s1==null && s2==null;
        }
        if(s1==null){
            //null，用equal会空指针
            return s3.equals(s2);
        }
        if(s2==null){
            return s3.equals(s1);
        }

        if(s1.length()+s2.length()!=s3.length()){
            return false;
        }
        //此时s1，s2，s3都不为null
        dfs(s1, s2, s3,0,0,0);
        return res;
    }

    private void dfs(String s1, String s2, String s3, int x1, int x2, int x3) {


        if(x1==s1.length() && x2==s2.length()){
            res=true;
            return;
        }

        if(x1<s1.length() && s1.charAt(x1)==s3.charAt(x3)){
            dfs(s1,s2,s3,x1+1,x2,x3+1);
        }

        if(x2<s2.length() && s2.charAt(x2)==s3.charAt(x3)){
            dfs(s1,s2,s3,x1,x2+1,x3+1);
        }
    }
```

### 115.不同的子序列

- 给定一个字符串 `s` 和一个字符串 `t` ，计算在 `s` 的子序列中 `t` 出现的个数
- 字符串的一个 子序列 是指，通过删除一些（也可以不删除）字符且不干扰剩余字符相对位置所组成的新字符串。（例如，"ACE" 是 "ABCDE" 的一个子序列，而 "AEC" 不是）

- dp

```java
    public int numDistinct(String s, String t) {
        if(s.length()<t.length()){
            return 0;
        }
        if(s.equals(t)){
            return 1;
        }

        int [][]dp=new int[t.length()+1][s.length()+1];
        dp[0][0]=1;
        for(int i=1;i<dp[0].length;i++){
            dp[0][i]=1;
        }

        for(int i=1;i<=t.length();i++){
            for (int j=1;j<=s.length();j++){
                dp[i][j]=dp[i][j-1];   //不匹配，则跟已经匹配的相同
                if(t.charAt(i-1)==s.charAt(j-1)) {
                    dp[i][j]+=dp[i-1][j-1];//匹配则加上   上一匹配的数量
                }
            }
        }
        return dp[t.length()][s.length()];
    }
```

### 135.分发糖果

- `n` 个孩子站成一排
  - 每个孩子至少分配到 `1` 个糖果。
  - 相邻两个孩子评分更高的孩子会获得更多的糖果
  - 相同分的没有要求
- dp
  - 但是时间复杂度是O(n2)

```java
class Solution {
    public int candy(int[] ratings) {
        int []nums=new int[ratings.length];


        //找到递减或递增序列，比前一个小则减一，大则加一，等则相同
        nums[0]=1;

        for(int i=1;i< nums.length;i++){
            if(ratings[i]<ratings[i-1]){  //往右是递减序列，先让最右为1
                nums[i]=1;
                if(nums[i-1]==1){  //和此时的1相等
                    for(int k=i-1;k>=0;k--){
                        //向左更新，不满足的则+1
                        if(nums[k]==nums[k+1]  && ratings[k]!=ratings[k+1]){
                            nums[k]++;
                        }
                    }
                }
            }else if(ratings[i]==ratings[i-1]){//相等则取一个最小值
                nums[i]=1;
            }else {
                nums[i]=nums[i-1]+1;   //向右递增，则在左一个节点的基础上加1
            }
        }

        int sum=0;
        for(int n:nums){
            sum+=n;
        }

        return sum;
    }
}
```

- 2次遍历
  - 一向左一向右
  - 已经能拿到所有的次序信息  O(n)
  - 下次再写
  
  ```java
  import java.util.*;
  class Solution {
      public int candy(int[] ratings) {
          int []left=new int[ratings.length];//从左往右
          int []right=new int[ratings.length];
          int sum=0;
          for(int i=1;i<ratings.length;i++){
              if(ratings[i]>ratings[i-1]){
                  left[i]=left[i-1]+1;
              } 
          }
          for(int i=ratings.length-2;i>=0;i--){
              if(ratings[i]>ratings[i+1]){
                  right[i]=right[i+1]+1;
              }   
          }
          //左和右只要最大值
          for(int i=0;i<ratings.length;i++){
              sum+=Math.max(left[i],right[i])+1;
          }
          return sum;
      }
  }
  ```
  
  

## ==双指针==

### 88.合并两个有序数组

- 两个非递减序列。nums1中有空位，非递减顺序合并后存储 在nums1中
  - 整数 `m` 和 `n` ，分别表示 `nums1` 和 `nums2` 中的非0元素数目
  - nums1 的初始长度为 m + n，其中前 m 个元素表示应合并的元素，后 n 个元素为 0
- 双指针

```java
public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i=m-1;
        int j=n-1;
        int index=m+n-1;

        //特殊情况nums1=[0],nums1全是0
        while(i==-1&&j>=0){
            nums1[index--]=nums2[j--];
        }

        while(i!=-1&&j>=0){
            //index从nums1后面开始放
            if(nums2[j]>=nums1[i]){
                nums1[index--]=nums2[j--];
            }else{
                nums1[index--]=nums1[i--];
            }
        }

        //nums1[0]>nums2[0]。此时i已经等于-1.第二个while已经不能满足
        while(i==-1&&j>=0){
            nums1[index--]=nums2[j--];
        }
    }
```

### 24.反转链表(剑指)

- 定义一个函数，输入一个链表的头节点，反转该链表并输出反转后链表的头节点。

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */

 /*
 双辅助指针。
 一个保存前节点，一个保存后结点
 当前指向前，后移
 */
class Solution {
    public ListNode reverseList(ListNode head) {
        //后指前，留个Next保存再后一个
        ListNode Next;
        ListNode pre=null;//前初始化为null
        while(head!=null){
            Next=head.next;//保存后结点
            head.next=pre;//当前指向前
            //后移
            pre=head;
            head=Next;
        }
        return pre;
    }
}
```

### 11.盛最多水的容器

- 只是找能盛最多水的2线

```java
/*
官方-双指针
谁低移动谁

because
取决于短板
移动高的，宽度再变小，高度不会高过当前的，所以容量不可能超过已有的。
*/
public int maxArea(int[] height) {

        int st=0;
        int end=height.length-1;
        int res=0;
        while (st<end){
            int temp=Math.min(height[st],height[end])*(end-st);
            if(temp>res){
                res=temp;
            }
            if(height[st]<height[end]){
                st++;
            }else {
                end--;
            }
        }
        return res;
    }
```

### 15.三数之和

- 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有和为 0 且不重复的三元组
- 排序+双指针

```java
public List<List<Integer>> threeSum(int[] nums) {
        int n=nums.length;
        List <List <Integer>> res=new ArrayList<>();
        if(n<3){
            //return null;不能返回null
            return res;
        }
        Arrays.sort(nums);
        for(int i=0;i<=n-3;i++){
            //防止i重复
            if(i>0 && nums[i]==nums[i-1]){
                continue;
            }
            int st=i+1;
            int end=n-1;
            while (st<end){
                int sum=nums[i]+nums[st]+nums[end];
                if(sum==0){
                    List<Integer> list=new ArrayList<>();
                    list.add(nums[i]);
                    list.add(nums[st]);
                    list.add(nums[end]);
                    res.add(list);
                    st++;
                    //防止st重复
                    while (st<n-1 && nums[st]==nums[st-1]){
                        st++;
                    }
                }else if(sum<0){
                    st++;
                }else {
                    end--;
                }
            }
        }
        return res;
    }
```



### 42.接雨水

- 给定 `n` 个非负整数表示每个宽度为 `1` 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
- 双指针

```java
    public int trap(int[] height) {
        int res=0;
        int i=0;
        int j=height.length-1;
        int high=Math.min(height[i],height[j]);
        boolean isForward=false;//因为要用到i或者j是不确定的
        if(high==height[i])
            isForward=true;

        while (i<j){
            if(isForward){//向左还是向右
                if(height[i]<=height[j]){
                    if(height[i]<=high)
                        res+=(high-height[i]);
                    else
                        high=height[i];
                    i++;
                }
                else {
                    high=height[j];
                    isForward=false;
                }
            }
            else {
                if(height[j]<=height[i]){
                    if(height[j]<high)
                        res+=(high-height[j]);
                    else
                        high=height[j];
                    j--;
                }else {
                    high=height[i];
                    isForward=true;
                }
            }
        }
        return res;
}
```

### 167.两数之和二

- 给你一个下标从 **1** 开始的整数数组 `numbers` ，该数组已按 非递减顺序排列 ，请你从数组中找出满足相加之和等于目标数 `target` 的两个数。要求常量级空间
- 二分查找加双指针

```java
public int[] twoSum(int[] numbers, int target) {
        int low=0;
        int high=numbers.length-1;

        //二分法找到第一个比target大的数的下标high。target小于numbers[0]的话，则不用再找，否则high=0
        //没啥用，就是为了优化某些不知死活的情况
        while(low<high && target>numbers[0]){
            int mid=low+((high-low)>>1);
            if(numbers[mid]>target){
                high=mid;
            }else{
                low=mid+1;
            }
        }

        //双指针
        int i=0;
        while(i<high){
            if(numbers[i]+numbers[high]==target){
                return new int[]{i+1,high+1};
            }else if(numbers[i]+numbers[high]>target){
                high--;
            }else {
                i++;
            }
        }
        return new int[2];
    }
```

### 344.反转字符串

- 双指针

```java
public void reverseString(char[] s) {
        int i = 0;
        int j = s.length - 1;
        while (i < j) {
            char c = s[i];
            s[i] = s[j];
            s[j] = c;
            i++;
            j--;
        }
    }
```

### 16.最接近的三数和

- nums[]和target，假设恰好只有一个解
- 排序+双指针。双指针sum有正负判断，所以是在不断靠近

```java
    public int threeSumClosest(int[] nums, int target) {
        int res=0;
        Arrays.sort(nums);
        int difference=Integer.MAX_VALUE;
        for(int i=0;i<nums.length-2;i++){
            int j=i+1;
            int k=nums.length-1;
            while (j<k){
                int sum=nums[i]+nums[j]+nums[k];
                if(sum==target){
                    return target;
                }
                if(Math.abs(sum-target)<difference){
                    difference=Math.abs(sum-target);
                    res=sum;
                }
                //居然用sum就可以控制了。。。毕竟sum有正负，每次都是靠近
                if(sum<target){
                    j++;
                }else{
                    k--;
                }
            }
        }
        return res;
    }
```

### 18.四数之和

- `nums[a] + nums[b] + nums[c] + nums[d] == target`
- 排序加双指针

```java
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> res=new ArrayList<>();
        Set<String>set=new HashSet<>();
        Arrays.sort(nums);
        int len=nums.length;
        for(int i=0;i<len;i++){
            for(int j=i+1;j<len;j++){
                int k=j+1;
                int x=len-1;
                while (k<x){
                    //[1000000000,1000000000,1000000000,1000000000] -294967296  int会反向越界
                    long sum=(long)nums[i]+(long)nums[j]+(long)nums[k]+(long)nums[x];
                    if(sum==target){
                        //拼接成String来去重
                        StringBuilder strb=new StringBuilder();
                        strb.append(nums[i]);
                        strb.append(nums[j]);
                        strb.append(nums[k]);
                        strb.append(nums[x]);

                        if(!set.contains(strb.toString())) {
                            List<Integer> node = new ArrayList<>();
                            node.add(nums[i]);
                            node.add(nums[j]);
                            node.add(nums[k]);
                            node.add(nums[x]);
                            res.add(node);
                            set.add(strb.toString());
                        }
                        k++;//随便更新个防止死循环，x--也行
                    }else if(sum<target){
                        k++;
                    }else {
                        x--;
                    }
                }
            }
        }
        return res;
    }
```

### 27.移除元素

- 给你一个数组 `nums` 和一个值 `val`，你需要 原地移除所有数值等于 `val` 的元素，并返回移除后数组的新长度。空间复杂度O(1)

- 双指针（优化了下

```java
//快慢指针    
public int removeElement(int[] nums, int val) {
        if(nums.length==0){
            return 0;
        }
        int count=0;
        int slow=0;
        int fast=0;
        while (fast<nums.length){
            if(nums[fast]!=val){
                count++;
                nums[slow++]=nums[fast];
            }
            fast++;
        }
        return count;
    }

//优化后，左右指针
    public int removeElement(int[] nums, int val) {
        if(nums.length==0){
            return 0;
        }
        int left=0;
        int right=nums.length-1;
        //优化[1,2,3,4,5] 1 移动多次，直接变成[5,2,3,4]
        while (left<=right){
            if(nums[left]==val){
                //直到左不再是val. 6啊
                nums[left]=nums[right--];//right来控制长度
            }else {
                left++;
            }
        }
        return left;
    }
```



## ==二分法==

### 4.寻找2个正序数组的中位数

- 去除前i或j个，更新

```java
 public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len=nums1.length+nums2.length;
        if (len % 2 == 1) {
            int midIndex = len / 2;
            double median = getKthElement(nums1, nums2, midIndex + 1);
            return median;
        } else {
            double median = (getKthElement(nums1, nums2, len/2) + getKthElement(nums1, nums2, len/2+ 1)) / 2.0;
            return median;
        }
    }

    public int getKthElement(int[] nums1, int[] nums2, int k) {
        
        int i = 0, j = 0;
        while (true) {
            // 边界情况
            if (i == nums1.length) {
                return nums2[j + k - 1];
            }
            if (j == nums2.length) {
                return nums1[i + k - 1];
            }
            if (k == 1) {
                return Math.min(nums1[i], nums2[j]);
            }

            // 正常情况
            int half = k / 2;
            int I = Math.min(i + half,nums1.length) - 1;
            int J = Math.min(j + half, nums2.length) - 1;
            int pivot1 = nums1[I], pivot2 = nums2[J];
            if (pivot1 <= pivot2) {
                k -= (I - i + 1);
                i = I + 1;
            } else {
                k -= (J - j + 1);
                j = J + 1;
            }
        }
    }
```

### 33. 搜索旋转排序数组

- 给你 **旋转后** 的前后分别有序数组 `nums` 和一个整数 `target` ，如果 `nums` 中存在这个目标值 `target` ，则返回它的下标，否则返回 `-1` 。数组中元素互不相同

```java
public int search(int[] nums, int target) {
        int len=nums.length;
        int j=findFirst(nums,0,len-1);//找到分界点(最小值)的下标

        if(target>nums[len-1])
            return binarySearch(nums,0,j-1,target);//对后二分搜索
        return binarySearch(nums,j,len-1,target);//前
    }

    private int findFirst(int []nums,int i,int j){//二分
        while (i<j){
            int mid=i+((j-i)>>1);
            if(nums[mid]>nums[j])
                i=mid+1;
            else
                j=mid;
        }
        return j;
    }
    private int binarySearch(int []nums,int i,int j,int tar){//二分
        while (i<=j){
            int mid=i+((j-i)>>1);
            if(nums[mid]==tar)
                return mid;
            if(nums[mid]<tar)
                i=mid+1;
            if(nums[mid]>tar)
                j=mid-1;
        }
        return -1;
    }
```

### 34.排序数组找元素的起始

- 升序整数数组 nums,目标值 target。target开始位置和结束位置。不存在则返回 [-1, -1]

```java
 public int[] searchRange(int[] nums, int target) {
        int len=nums.length;
        if(len==0 || target<nums[0] || target>nums[len-1]){
            return new int[]{-1,-1};
        }
        int st=findSt(nums,0,len-1,target);
        int end=findEnd(nums,0,len-1,target);
        return new int[]{st,end};
    }
    
//第一个比tar小的数下标
    private int findSt(int []nums,int low,int high,int tar){
        int n=high;
        while (low<=high){
            int mid=low+((high-low)>>1);
            if(nums[mid]>=tar)
                high=mid-1;
            else
                low=mid+1;
        }
        if(n==high)
            return nums[n]==tar?n:-1;
        return nums[high+1]==tar?high+1:-1;
    }

//第一个比tar大的数下标
    private int findEnd(int []nums,int low,int high,int tar){
        int n=low;
        while (low<=high){
            int mid=low+((high-low)>>1);
            if(nums[mid]<=tar)
                low=mid+1;
            else
                high=mid-1;
        }
        if(n==low)
            return nums[n]==tar?n:-1;
        return nums[low-1]==tar ?low-1:-1;
    }
```

### 704.二分查找

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

### 278.第一个错误的版本

  - 假设你有 `n` 个版本 `[1, 2, ..., n]`，你想找出导致之后所有版本出错的第一个错误的版本
  - 注意和经典二分查找有点不同
  - 分界点可low<high    low+1

```java
 public int firstBadVersion(int n) {
        int low=1;
        int high=n;

        while(low<high){
            int mid=low+((high-low)>>1);
            if(isBadVersion(mid)){
                high=mid;//high=mid-1;的话可能会跳过true的结果
            }else{
                low=mid+1;
            }
        }
        return low;//收缩为一点
    }
```

### 35.搜索插入位置

- 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
- 分界点可low<high    low+1

```java
public int searchInsert(int[] nums, int target) {
        int l=0;
        int r=nums.length;
        while(r<l){
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

### 74.搜索二维矩阵

- 编写一个高效的算法来判断 m x n 矩阵中，是否存在一个目标值。该矩阵具有如下特性：每行中的整数从左到右按升序排列。每行的第一个整数大于前一行的最后一个整数
- 二分查找，先找行再找列

```java
    public boolean searchMatrix(int[][] matrix, int target) {
        int m=matrix.length;
        int n=matrix[0].length;

        int mleft=0;
        int mright=m-1;
        int nleft=0;
        int nright=n-1;

        //先找所在行
        int x=-1;
        while (mleft<=mright){
            int mid=(mleft+mright)/2;
            if(target>=matrix[mid][0] && target<=matrix[mid][n-1]){
                x=mid;
                break;
            }
            if(target<matrix[mid][0]){
                mright=mid-1;
            }
            if(target>matrix[mid][n-1]){
                mleft=mid+1;
            }
        }
        if(x==-1){
            return false;
        }

        while (nleft<=nright){
            int mid=(nleft+nright)/2;
            if(target==matrix[x][mid]){
                return true;
            } else if (target<matrix[x][mid]) {
                nright=mid-1;
            }else {
                nleft=mid+1;
            }
        }
        return false;
    }
```



##  ==快慢指针==

### 19.删除链表的倒数第N个节点

- 快指针先走n步

```java
   public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode pr=head;
        ListNode nex=head;
        while(n>0){//快指针nex先走n步
            nex=nex.next;
            n--;
            if(nex==null)//说明删除的是第一个节点
                return head.next;
        }
        while (nex.next!=null){//nex走到最后
            nex=nex.next;
            pr=pr.next;
        }
        pr.next=pr.next.next;//修改
        return head;
    }
```

### 26.删除有序数组中的重复项

- 要求常数空间
- 快慢指针

```java
//    public int removeDuplicates(int[] nums) {
//        int count=0;
//        int st=0;//当前元素位置
//        Set<Integer> set=new HashSet<>();
//        for(int i=0;i<nums.length;i++){
//            if(!set.contains(nums[i])){
//                set.add(nums[i]);
//                if(i!=st){
//                    nums[st]=nums[i];
//                }
//                st++;
//                count++;
//            }
//        }
//        return count;
//    }
    //题目要求不使用额外空间
    public int removeDuplicates(int[] nums) {
        if(nums.length==0){
            return 0;
        }
        //快慢指针，因为有fast-1，所以slow初始值是1，第一个不会重复
        int slow=1;//慢当前填入位置
        int fast=1;//快下一个要填入的数
        while (fast<nums.length){
            if(nums[fast]!=nums[fast-1]){
                nums[slow++]=nums[fast];
            }
            fast++;
        }
        return slow;//slow就是个数。。
    }
```

### 141.环形链表

- 快慢指针

```java
    public boolean hasCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast!=null && fast.next!=null) {
            fast=fast.next.next;
            slow=slow.next;
            if(fast==slow){
                return true;
            }
        }
        return false;
    }
```

### 142.环形链表二

- 若有环，找到入环点

- Set

```java
    public ListNode detectCycle(ListNode head) {
        Set<ListNode>set=new HashSet<>();
        while (head!=null){
            if(set.contains(head)){
                return head;
            }
            set.add(head);
            head=head.next;
        }
        return null;
    }
```

- 快慢指针（官方
  - 一个走一步，一个走2步，有环且它们相遇时
  - 有了 a=c+(n-1)(b+c)的等量关系
  - 在用一个指针从起始点出发

```java
public class Solution {
    public ListNode detectCycle(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode slow = head, fast = head;
        while (fast != null) {
            slow = slow.next;
            if (fast.next != null) {
                fast = fast.next.next;
            } else {
                return null;
            }
            if (fast == slow) {
                ListNode ptr = head;
                while (ptr != slow) {
                    ptr = ptr.next;
                    slow = slow.next;
                }
                return ptr;
            }
        }
        return null;
    }
}
```

### 109.有序链表转为二叉搜索树

- 快慢指针找中间根节点（官方

```java
class Solution {
    public TreeNode sortedListToBST(ListNode head) {
        return buildTree(head, null);
    }

    public TreeNode buildTree(ListNode left, ListNode right) {
        if (left == right) {
            return null;
        }
        ListNode mid = getMedian(left, right);
        TreeNode root = new TreeNode(mid.val);
        root.left = buildTree(left, mid);
        root.right = buildTree(mid.next, right);
        return root;
    }

    public ListNode getMedian(ListNode left, ListNode right) {
        ListNode fast = left;
        ListNode slow = left;
        while (fast != right && fast.next != right) {
            fast = fast.next;
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }
}
```



## ==简单回溯==

- 先把框架写好了什么都好说
- 建议变量定义为字段
- 不如想想解的空间树

### 22.括号生成

- 数字 `n` 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 **有效的** 括号组合。

```java
private int len;//保存初始n的值
    public List<String> generateParenthesis(int n) {
        List<String> res=new ArrayList<>();
        len=n;
        creThe(res,new StringBuilder(),0,0,n*2);
        return res;
    }

	/**
	*left (数量  right  )数量 
	*/
    private void creThe(List<String> list,StringBuilder str,int left,int right,int n){
        //控制右比左少，左比初始n少
        if(right>left || left>len)//不符合
            return;
        if(n==0){//结束
            list.add(str.toString());
            return;
        }
        StringBuilder str1=new StringBuilder(str);//复制
        str.append('(');
        str1.append(')');
        creThe(list,str,left+1,right,n-1);
        creThe(list,str1,left,right+1,n-1);
    }
```

### 46.全排列

- 给定一个不含重复数字的数组 `nums` ，返回其 所有可能的全排列。你可以 **按任意顺序** 返回答案。
- 回溯

```java
  public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res=new LinkedList<>();//LinkedList删除节点好点
        for(int i=0;i<nums.length;i++){
            List<Integer>node=new ArrayList<>();
            node.add(nums[i]);
            res.add(node);//创建n初始值
        }
        getSeq(res,nums,nums.length);
        return res;
    }
    private void getSeq(List<List<Integer>>lists,int []nums,int n){
        if(n==1)
            return;
        int size=lists.size();
        for(int i=0;i<size;i++){
            for(int e:nums){
                List<Integer>node=new ArrayList<>(lists.get(i));//每次再添加n个
                node.add(e);//每个后面都带个不同的数
                if(!isSame(node))//没有重复则满足
                    lists.add(node);
            }
        }
        
        for(int i=0;i<size;i++)//删除前一批
            lists.remove(0);
        getSeq(lists,nums,n-1);

    }

    //有重复元素返回true
    private boolean isSame(List<Integer>list){
        HashSet<Integer>set=new HashSet<>();
        for(int e:list){
            if(!set.contains(e))
                set.add(e);
            else
                return true;
        }
        return false;
    }
```

### 90.子集二

- 给你一个整数数组 `nums` ，其中可能包含重复元素，请你返回该数组所有可能的子集（幂集），不能重复
- 回溯

```java
    public List<List<Integer>> subsetsWithDup(int[] nums) {

        List<List<Integer>>res=new ArrayList<>();
        res.add(new ArrayList<>());
        Arrays.sort(nums);
        dfs(res,nums,0,0);
        return res;
    }

    private void dfs(List<List<Integer>> lists, int[] nums,int index,int start) {
        if(index==nums.length){
            return;
        }
        int size=lists.size();
        for(int i=start;i<size;i++){
            List<Integer>list=new ArrayList<>(lists.get(i));
            list.add(nums[index]);
            lists.add(list); //lists长度乘二
        }
        //和上一个重复则只对新加入的节点遍历
        if(index<nums.length-1 && nums[index+1]==nums[index]){
            dfs(lists,nums,index+1,size);
        }else {
            dfs(lists,nums,index+1,0);
        }
    }
```

### 92.复原ip地址

- 给定一个只包含数字的字符串 `s` ，用以表示一个 IP 地址，返回所有可能的**有效 IP 地址**，这些地址可以通过在 `s` 中插入 `'.'` 来形成，s长度为[1,21]
- 回溯

```java
    List<String>res;
    int []subPoints;//分割点

    int count=0;//记录分割的次数
    public List<String> restoreIpAddresses(String s) {
        //s长度为[1,21]  也就是可能包含不合法的
        res=new ArrayList<>();
        subPoints=new int[5];
        subPoints[0]=0;
        dfs(s,0);
        return res;
    }

    private void dfs(String s, int index) {
        if(index==s.length() && count<4){//到了结尾还没有四次分割
            return;
        }
        if(count==4){
            //到4后就不再分割
            if(index==s.length()){
                StringBuilder strb=new StringBuilder();
                for(int i=0;i<subPoints.length-1;i++){
                    strb.append(s.substring(subPoints[i], subPoints[i + 1])).append(".");
                }
                strb.deleteCharAt(strb.length()-1); //删除最后一个.
                res.add(strb.toString());
            }
            return;
        }
        if(s.charAt(index)=='0'){
            count++;
            subPoints[count]=index+1;
            dfs(s,index+1);
            count--;
        }else {
            for(int i=0; i<3;i++){
                //没有前导0且至少还有一个字符未处理，所以肯定可以进行下一次dfs
                if(index+3-i<=s.length() && Integer.parseInt(s.substring(index,index+3-i))<=255){
                    count++;
                    subPoints[count]=index+3-i;
                    dfs(s,index+3-i);
                    count--;
                }
            }
        }
    }
```

### 129.求根节点到叶节点数字之和

- 从根节点到叶节点的路径 `1 -> 2 -> 3` 表示数字 `123`
- dfs

```java
    int res=0;
    List<Integer>list;
    public int sumNumbers(TreeNode root) {
        list=new ArrayList<>();
        dfs(root);
        return res;
    }

    private void dfs(TreeNode root){
        if(root==null){
            return;
        }
        list.add(root.val);
        if(root.left==null && root.right==null){
            int sum=0;
            for(Integer e:list){
                sum=sum*10+e;
            }
            res+=sum;
            //因为return了，所以这里先清除下
            list.remove(list.size()-1);
            return;
        }
        dfs(root.left);
        dfs(root.right);
        list.remove(list.size()-1);
    }
```



## ==回溯复原==

- 。。 先用一条走到尽头

### 51.N皇后

- 回溯

```java
public List<List<String>> solveNQueens(int n) {
        int []nums=new int [n]; //下标对应列，值表示Q在该列的位置
        for(int i=0;i<n;i++)//赋值-100，-1的话会和0形成对角线
            nums[i]=-100; //-100表示该列没有放Q
        List<List<String>> res=new ArrayList<>();
        getNums(nums,0,n,res);
        return res;
    }
    private void getNums(int []nums,int index,int n,List<List<String>> res){
        if(index==n){   //已经放够了
            List<String> list=new ArrayList<>();
            for(int e:nums){
                StringBuilder str=new StringBuilder();
                for(int i=0;i<n;i++){
                    if(i!=e)
                        str.append(".");
                    else
                        str.append("Q");
                }
                list.add(str.toString());
            }
            res.add(list);
            return;
        }

        for(int j=0;j<n;j++){
            nums[index]=j;   //每个位置都试着放一下
            if(!isAttack(nums))
                getNums(nums,index+1,n,res);
            nums[index]=-100;//从后往前回的时候复原,复原要在递归之后
        }

    }
    /*
    判断能否攻击到
    能攻击到就ture
    */
    private boolean isAttack(int []nums){
        //是否同一列
        Set<Integer> set=new HashSet<>();
        for(int e:nums){
            if(e==-100)    //从第一行往下放，e是-100，说明后面的也是-100
                break;
            if(!set.contains(e))
                set.add(e);
            else
                return true;
        }

        //是否对角线
        for(int i=0;i<nums.length;i++){
            for(int j=0;j<nums.length;j++){
                if(j!=i){
                    //上下左右一共4个角
                    if(nums[j]-nums[i]==j-i || nums[j]-nums[i]==i-j){
                        return true;
                    }
                }
            }

        }
        return false;
    }
```

### 52.N皇后二

- 我都不想写，用51的代码
- 答案数量不多，打表法

### 17.电话号码的字母组合

- 九宫格打字

```java
    /**
     * 复原回溯法
     * 一个对象new StringBuilder()能够重复使用
     * 长度够之后返回
     */
    public List<String> letterCombinations(String digits) {
        List<String>res=new ArrayList<>();
        if(digits.length()==0){
            return res;
        }
        //新写法
        Map<Character, String>map=new HashMap<>(){
            {
                put('2',"abc");
                put('3',"def");
                put('4',"ghi");
                put('5',"jkl");
                put('6',"mno");
                put('7',"pqrs");
                put('8',"tuv");
                put('9',"wxyz");
            }
        };
        getList(res,map,digits,0,new StringBuilder());
        return res;
    }

    private void getList(List<String> list,Map<Character,String> map,
                         String digits,int index,StringBuilder strb){
        if(strb.length()==digits.length()){
            list.add(strb.toString());
            return;
        }
        //index表示第几个号码
        String digit=map.get(digits.charAt(index));
        
        for(int i=0;i<digit.length();i++){
            strb.append(digit.charAt(i));
            getList(list,map,digits,index+1,strb);
            strb.deleteCharAt(index);
        }
    }
```

### 39.组合总数

- 给你一个 无重复元素 的整数数组 candidates 和一个目标整数 target ，找出 candidates 中可以使数字和为目标数 target 的 所有 不同组合 。数组元素互异

- 回溯复原，从大的数开始。因为要复原，所以加结果时要new

```java
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>>res=new ArrayList<>();
        List<Integer>list=new ArrayList<>();
        getCand(candidates,res,list,candidates.length-1,target);
        return res;
    }

    private void getCand(int []candidates,List<List<Integer>>res,List<Integer>list,int index,int target){
        int sum=0;
        for (Integer e:list){
            sum+=e;
        }
        if(sum==target){
            //res.add(list);这个是引用，后面会清空，所以要new
            res.add(new ArrayList<>(list));
            return;
        }
        if(sum>target){
            return;
        }

        for (int i=index;i>=0;i--){
            list.add(candidates[i]);
            getCand(candidates,res,list,i,target);
            list.remove(list.size()-1);
        }
    }
```

### 47.全排列二

- 给定一个可包含重复数字的序列 `nums` ，***按任意顺序*** 返回所有不重复的全排列
- 回溯复原，并去重

```java
    List<List<Integer>>res=new ArrayList<>();
    List<Integer>list=new ArrayList<>();

    boolean[]isvisited;

    public List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        isvisited=new boolean[nums.length];
        getRes(nums,0);
        return res;
    }

    private void getRes(int [] nums,int n){
        if(n==nums.length){
            res.add(new ArrayList<>(list));
            return;
        }

        for(int i=0;i<nums.length;i++){
            if(i>0 && isvisited[i-1] &&  nums[i]==nums[i-1]){ //去重
                continue;
            }
            if(!isvisited[i]){
                isvisited[i]=true;
                list.add(nums[i]);
                getRes(nums,n+1);
                list.remove(list.size()-1);
                isvisited[i]=false;
            }
        }
    }
```

### 77.组合

- 给定两个整数 `n` 和 `k`，返回范围 `[1, n]` 中所有可能的 `k` 个数的组合。你可以按 **任何顺序** 返回答案
- 复原回溯

```java
    List<List<Integer>>lists;
    List<Integer>list;

    public List<List<Integer>> combine(int n, int k) {
        lists=new ArrayList<>();
        list=new ArrayList<>();
        getRes(n,k,1);
        return lists;
    }

    private void getRes(int n, int k,int index) {
        if(list.size()==k){
            List<Integer>node=new ArrayList<>(list);
            lists.add(node);
            return;
        }
        for(int i=index;i<=n;i++){
            list.add(i);
            getRes(n,k,i+1);
            list.remove(list.size()-1);
        }
    }
```

### 79.单词搜索

- 给定一个 `m x n` 二维字符网格 `board` 和一个字符串单词 `word` 。如果 `word` 存在于网格中，返回 `true` ；否则，返回 `false`
- 二维平面dfs搜索  复原回溯

```java
    int [][]directions=new int[][]{{-1,0}, {1,0},{0,1},{0,-1}};//四个方向，左右上下

    boolean ans;
    public boolean exist(char[][] board, String word) {
        boolean [][]visited=new boolean[board.length][board[0].length]; //防止回头
        for(int i=0; i<board.length; i++){
            for (int j=0; j<board[0].length; j++){
                dfs(board,word,visited,i,j,0);
            }
        }
        return ans;
    }

    private void dfs(char[][] board, String word, boolean [][] visited,int x, int y,int index) {
        //index>0 让第一个进入，不然无法递归。ans 有结果就不计算了
        if(x<0 || x==board.length || y<0 || y==board[0].length || visited[x][y] || board[x][y]!=word.charAt(index) || ans){
            return;
        }
        if(index==word.length()-1){
            ans=true;
            return;
        }
        visited[x][y]=true;
        index++;
        for (int[] direction : directions) {
            dfs(board, word, visited,x + direction[0], y + direction[1], index);
        }
        visited[x][y]=false;
    }
```



## ==递归==

### 78.子集

- 给你一个整数数组 `nums` ，数组中的元素 **互不相同** 。返回该数组所有可能的子集（幂集）。

```java
//先复制，再加元素
//递归，不回溯，因为没啥可判断的。    
public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>>res=new ArrayList<>();
        res.add(new ArrayList<Integer>());
        getSeq(res,nums,nums.length,0);
        return res;
    }
//nums = [1,2,3]   子集而已，里面唯一就行，顺序不要求     
//[]->[],[1]->[],[1],[2],[1,2]->[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]
    private void getSeq(List<List<Integer>> list,int []nums,int n,int ix){//ix,当前已经用了的下标
        if(n==0)
            return;
        int size=list.size();
        for(int i=0;i<size;i++){//先操作完一批，再递归下一个
            List<Integer> node=new ArrayList<>(list.get(i));//复制
            node.add(nums[ix]);//把值加进去
            list.add(node);//节点加到结果
        }
        getSeq(list, nums, n-1, ix+1);
    }
```

### 生成二进制序列

- 递归

```java
//n位   00,01,10,11
public  void  getSeq(List<StringBuilder> list,int n){
        int len=(int)Math.pow(2,n);
        for(int i=0;i<len;i++){
            list.add(new StringBuilder());
        }
        doSeq(list,0,len);
}
private void doSeq(List<StringBuilder> list,int st,int end){
        if(end-st==1){
            return;
        }
        int mid=(end+st)/2;
    	//一半一半
        for(int i=st;i<mid;i++){
            list.get(i).append("0");
        }
        for(int j=mid;j<end;j++){
            list.get(j).append("1");
        }
        doSeq(list,st,mid);
        doSeq(list,mid,end);
}
```

### 60.排列序列

- 给出集合 `[1,2,3,...,n]`，其所有元素共有 `n!` 种排列。给定 `n` 和 `k`，返回第 `k` 个排列

- 找规律，递归

```java
    List<Integer>list;
    StringBuilder strb;
    public String getPermutation(int n, int k) {
        list=new ArrayList<>();
        strb=new StringBuilder();
        int total=1;
        for(int i=1;i<=n;i++){
            list.add(i);
            total*=i;
        }
        getRes(total,n,k-1);//k-1是为了下标从0开始
        return strb.toString();
    }

    /**
     *
     * @param total
     * @param n
     * @param k
     * 分组求，把所以结果序列从小到大放在一起就会发现，每次取一个下标为商所在分组，把该数取了后余数作为新的k
     * 也就是一位位，递归求
     */
    private void getRes(int total, int n, int k) {
        if(n==1){
            strb.append(list.remove(0));
            return;
        }
        int quotient=k/(total/n);//商，也就是要拿list中的下标的数。
        strb.append(list.remove(quotient));
        int remainder=k%(total/n);//余数
        getRes(total/n,n-1,remainder);
    }
```



## ==分治==

### 50.Pow(x,n)

- 分治法快速幂乘
- 递归     时间O(longn)   空间O(longn)

```java
    public double myPow(double x, int n) {
        //n可能是Integer.MIN_VALUE。转为long就没错
        //n可正可负可0
        return n>0?subPow(x,n):1/subPow(x,-(long) n);
    }

    private double subPow(double x,long n){
        if(n==1){
            return x;
        }else if(n==0){
            return 1;
        }
        else  if((n&1)==0){//偶数
            double d=subPow(x,n>>1);
            return d*d;
        }else {
            return subPow(x,n-1)*x;
        }
    }
```

- 迭代     时间O(longn)    空间O(1)
  - 压缩并更新权重

```java
    public double myPow(double x, int n) {
        //n可能是Integer.MIN_VALUE。转为long就没错
        //n可正可负可0
        return n>0?subPow(x,n):1/subPow(x,-(long) n);
    }
 
    private double subPow(double x,long n){
        double d=x;//权重从小(x)开始
        double res=1;
        while (n>0){
            if((n&1)==1){//只更新余数
                res*=d;
            }
            n/=2;//n不断压缩
            d*=d;//压缩后更新权重
        }
        return res;
    }
```

### 快速排序

- 

```java
    public void quickSort(int []nums){
        quick(nums,0,nums.length-1);

    }

    private void quick(int []nums,int start,int end){
        if(end-start<1){
            return;
        }

        //比n小则交换，并把n放到当前应该在的位置index，index++。比n大则不变
        int n=nums[start];
        int index=1+start;
        for(int i=start+1;i<=end;i++){
            if(nums[i]<n){
                swap(nums,i,index-1);
                if(i!=index){//i和index中间有大于n的数
                    swap(nums,index,i);
                }
                index++;
            }
        }
        quick(nums,start,index-2);
        quick(nums,index,end);
    }

    private void swap(int []nums,int i,int j){
        int temp=nums[i];
        nums[i]=nums[j];
        nums[j]=temp;
    }
```



## ==贪心==

### 55.跳跃游戏

- 数组中的每个元素代表你在该位置可以跳跃的最大长度。判断你是否能够到达最后一个下标。

```java
//贪心    
public boolean canJump(int[] nums) {
        int n=nums.length;
        int max=0;  //记录当前能到达的最远位置

        for(int i=0;i<n-1;i++){
            if(i>max)
                return false;
            max=Math.max(i+nums[i],max);
        }
        return max>=n-1;
    }
```

### 13.罗马数字转整数

- 贪心匹配,尽可能匹配多

```java
    public int romanToInt(String s) {
        int res=0;
        Map<String ,Integer>map=new HashMap<>();
        map.put("M",1000);
        map.put("CM",900);
        map.put("D",500);
        map.put("CD",400);
        map.put("C",100);
        map.put("XC",90);
        map.put("L",50);
        map.put("XL",40);
        map.put("X",10);
        map.put("IX",9);
        map.put("V",5);
        map.put("IV",4);
        map.put("I",1);
        
        int i=0;
        while(i<s.length()){
            int j=2;//最多有2位匹配
            //substring不越界
            if(i+j==s.length()+1){
                j=1;
            }
            //j至少为1保证i能增加
            while (j>=1){
                String str=s.substring(i,i+j);
                //贪心匹配
                if(map.containsKey(str)){
                    res+=map.get(str);
                    i+=j;
                    break;
                }else {
                    j--;
                }
            }
        }
        return res;
    }
```

### 45.跳跃游戏二

- 最少跳跃数到达最后位置（总能到达）
- nums表示当前能跳的最大位置
- 贪心法

```java
    //越简洁越好
	public int jump(int[] nums) {
       int res=0;
       int right=0;
       int next=0;//下一右边界
       for(int i=0;i<nums.length-1;i++){
           next=Math.max(next,nums[i]+i);//秒啊，每次更新一次，就不用再写一个循环。若存在不能跳到的话就另说
           if(i==right){//到达右边界
               right=next;
               res++;
           }
       }
       return res;
    }
```

### 122.买股票的最佳时机二

- 每一天，你可以决定是否购买和/或出售股票。你在任何时候 **最多** 只能持有 **一股** 股票。你也可以先购买，然后在 **同一天** 出售。

- 贪心       不是dp，因为没有用到前面的状态

```java
    public int maxProfit(int[] prices) {

        int profit=0;
        for(int i=0;i<prices.length-1;i++){
            if(prices[i]<prices[i+1]){
                //上升则加
                profit+=(prices[i+1]-prices[i]);
            }
        }
        return profit;
    }
```

### 120.三角形最小路径和

-  给定一个三角形 `triangle` ，找出自顶向下的最小路径和。

- 因为有负数，所以用贪心（迪杰斯特拉）好像不太行
- 迪杰斯特拉（有负数就瘫痪了

```java
    public int minimumTotal(List<List<Integer>> triangle) {

        int res=0;

        List<Integer>indexArr=new ArrayList<>();
        indexArr.add(0);

        // 上一层是4 4 4 4    下一层是5 5 9 5 5 5呢  所以要拿到所有下一层可以访问的下标
        for(int i=0;i<triangle.size();i++){

            int minVal=getMin(indexArr,triangle.get(i));
            res+=minVal;

            //从当前行能去的拿到下一行能去的
            List<Integer>nextIndex=new ArrayList<>();
            for(int j=0;j<indexArr.size();j++){
                int index=indexArr.get(j);
                if(triangle.get(i).get(index)==minVal){
                    nextIndex.add(index);
                    nextIndex.add(index+1); //能够去j+1  即使下标有重复也不管了
                }
            }
            indexArr=nextIndex;
        }
        return res;
    }

    private int getMin(List<Integer>indexArr,List<Integer>list){
        int min=list.get(indexArr.get(0));
        for(int i=1;i<indexArr.size();i++){
            int index=indexArr.get(i);
            if(list.get(index)<min){
                min=list.get(index);
            }
        }
        return min;
    }
```



- 有负权值，那就全部都算，累计求和（dp）
  - 这么说图的最短路径问题可以归结为dp？或者说贪心

```java
    public int minimumTotal(List<List<Integer>> triangle) {
        int n=triangle.size();

        int [][]arr=new int[n][n];
        arr[0][0]=triangle.get(0).get(0);
        for(int i=1;i<n;i++){
            List<Integer>list=triangle.get(i);
            for(int j=0;j<list.size();j++){

                int min=Integer.MAX_VALUE;
                if(j-1>=0 ){
                    //上一行的值
                    min=Math.min(min,arr[i-1][j-1]);
                }
                if(j<triangle.get(i-1).size()){
                    min=Math.min(min,arr[i-1][j]);
                }
                arr[i][j]=min+list.get(j);
            }
        }
        int res=arr[n-1][0];
        for(int i=1;i<arr[n-1].length;i++){
            if(arr[n-1][i]<res){
                res=arr[n-1][i];
            }
        }
        return  res;
    }

```



## ==DFS==

### 130.被围绕的区域

- 二维数组，把被围绕的O变为X
- 深度优先
  - 不仅仅用于图，只是一种搜索方式

```java
    public void solve(char[][] board) {

        //要2个for，不能一个
        for(int i=0;i<board[0].length;i++){
            dfs(board,0,i);
            dfs(board,board.length-1,i);
        }
        for(int i=1;i<board.length-1;i++){
            dfs(board,i,0);
            dfs(board,i,board[0].length-1);
        }

        for(int i=0;i<board.length;i++){
            for (int j=0;j<board[0].length;j++){
                if(board[i][j]=='O'){
                    board[i][j]='X';
                    continue;
                }
                if(board[i][j]=='M'){//复原
                    board[i][j]='O';
                }
            }
        }

    }

    //标记dfs,从边界搜索二维数组
    public void dfs(char[][]board,int i,int j){
        //!='O'  排除了M(防止死循环)，和X     不是O就不能往dfs前走了
        if(i>=board.length || i<0 || j>=board[0].length || j<0 || board[i][j]!='O'){
            return;
        }
        board[i][j]='M'; //能够遍历进来，说明O可达
        dfs(board,i-1,j);
        dfs(board,i+1,j);
        dfs(board,i,j-1);
        dfs(board,i,j+1);
    }
```

- 广度优先
- 并查集
  - 把二维的坐标转为一维坐标，一个连通区域算一个集合。假设一开始每个坐标都是一个集合

### 200.岛屿数量

- 1陆地，0是水
- 深度优先

```java
    int m,n;
    public int numIslands(char[][] grid) {
        m=grid.length;
        n=grid[0].length;
        int count=0;
        boolean[][] marks=new boolean[m][n];
        //Arrays.fill(marks,false); 二维数组好像不太行  只能一维基本类型数组
        for (boolean[] mark : marks) {
            Arrays.fill(mark, false);
        }

        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(grid[i][j]=='1' && !marks[i][j]){
                    count++;//在这里统计就好了
                    dfs(grid,marks,i,j);//标记完所有1
                }
            }
        }
        return count;
    }

    public void dfs(char[][]grid,boolean[][]marks,int i,int j){
        if(i>=m || i<0 || j>=n || j<0 || grid[i][j]=='0' || marks[i][j]){
            return;
        }
        marks[i][j]=true;
        dfs(grid,marks,i-1,j);
        dfs(grid,marks,i+1,j);
        dfs(grid,marks,i,j-1);
        dfs(grid,marks,i,j+1);
    }
```

- 广度优先
- 并查集

### 547.省份数量

- 都是在dfs之前count++，dfs只是为了标记

```java
    int n;
    public int findCircleNum(int[][] isConnected) {
        int count=0;
        n=isConnected[0].length;
        boolean[]visited=new boolean[n];
        Arrays.fill(visited,false);
        for(int i=0;i<n;i++){
            if(!visited[i]){//只在这里count++
                count++;
                dfs(i,isConnected,visited);
            }
        }
        return count;
    }

    private void dfs(int i,int [][]isConnected,boolean[]visited){
        for(int j=0;j<n;j++){
            if(isConnected[i][j]==1 && !visited[j]){
                visited[j]=true;
                dfs(j,isConnected,visited);
            }
        }
    }
```

## ==滑动窗口==

- 双指针，先移动右指针j，再移动左指针i。
- 窗口大小可变可不变
- 用一个变量，数组，Set等容器来存储当前数据，若右到达边界后是要更新容器并移动左指针
- 关键在于选择什么结构来存储数据

### 209.长度最小的子数组

- sum(nums,i,j)>=target

```java
    public int minSubArrayLen(int target, int[] nums) {
        int len=Integer.MAX_VALUE;
        int left=0;
        int right=0;
        int sum=0;
        while (right<nums.length){
            sum+=nums[right];
            while (sum>=target){
                len=Math.min(len,right-left+1);
                sum-=nums[left++];
            }
            right++;
        }
        return len==Integer.MAX_VALUE?0:len;
    }
```

### 219.存在重复的元素二

- 存在nums[i] == nums[j]` 且 `abs(i - j) <= k？
- 暴力

```java
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        for(int i=0;i<nums.length;i++){
            for(int j=i+1;j<=i+k && j<nums.length;j++){
                if(nums[i]==nums[j]){
                    return true;
                }
            }
        }
        return false;
    }
```

- 滑动窗口
  - 维持一个存了nums[i-k-1]-nums[i-1] 的HashSet

```java
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Set<Integer>set=new HashSet<>();
        for(int i=0;i<nums.length;i++){
            if(i>k){
                //未必需要有明显的另一个指针
                set.remove(nums[i-k-1]);//一进一出完成更新
            }
            if(!set.add(nums[i])){
                return true;
            }
        }
        return false;
    }
```

### 76.最小覆盖字串

- 传统先移动i，再j会超出时间限制
- 抄的

```java
    public String minWindow(String s, String t) {
        if (s == null || s == "" || t == null || t == "" || s.length() < t.length()) {
            return "";
        }
        //维护两个数组，记录已有字符串指定字符的出现次数，和目标字符串指定字符的出现次数
        //ASCII表总长128
        int[] need = new int[128];
        int[] have = new int[128];

        //将目标字符串指定字符的出现次数记录
        for (int i = 0; i < t.length(); i++) {
            need[t.charAt(i)]++;
        }

        //分别为左指针，右指针，最小长度(初始值为一定不可达到的长度)
        //已有字符串中目标字符串指定字符的出现总频次以及最小覆盖子串在原字符串中的起始位置
        int left = 0, right = 0, min = s.length() + 1, count = 0, start = 0;
        while (right < s.length()) {
            char r = s.charAt(right);
            if (need[r] == 0) {
                right++;
                continue;
            }
            //当且仅当已有字符串目标字符出现的次数小于目标字符串字符的出现次数时，count才会+1
            //是为了后续能直接判断已有字符串是否已经包含了目标字符串的所有字符，不需要挨个比对字符出现的次数
            if (have[r] < need[r]) {
                count++;
            }
            //已有字符串中目标字符出现的次数+1
            have[r]++;
            //移动右指针
            right++;
            //当且仅当已有字符串已经包含了所有目标字符串的字符，且出现频次一定大于或等于指定频次
            while (count == t.length()) {
                //挡窗口的长度比已有的最短值小时，更改最小值，并记录起始位置
                if (right - left < min) {
                    min = right - left;
                    start = left;
                }
                char l = s.charAt(left);
                //如果左边即将要去掉的字符不被目标字符串需要，那么不需要多余判断，直接可以移动左指针
                if (need[l] == 0) {
                    left++;
                    continue;
                }
                //如果左边即将要去掉的字符被目标字符串需要，且出现的频次正好等于指定频次，那么如果去掉了这个字符，
                //就不满足覆盖子串的条件，此时要破坏循环条件跳出循环，即控制目标字符串指定字符的出现总频次(count）-1
                if (have[l] == need[l]) {
                    count--;
                }
                //已有字符串中目标字符出现的次数-1
                have[l]--;
                //移动左指针
                left++;
            }
        }
        //如果最小长度还为初始值，说明没有符合条件的子串
        if (min == s.length() + 1) {
            return "";
        }
        //返回的为以记录的起始位置为起点，记录的最短长度为距离的指定字符串中截取的子串
        return s.substring(start, start + min);
    }
```

## ==位运算==

- 有时也用int来存储数据

### 187.重复的DNA序列

- 对于一些小的字符串或字符，可以考虑用int来存储。返回所有在 DNA 分子中出现不止一次的 长度为 10 的序列(子字符串)
- 把str用int存储。。
  - HashMap+滑动窗口+位运算    。优化了存储。。。

```java
    static final int L = 10;
    Map<Character, Integer> bin = new HashMap<Character, Integer>() {{
        put('A', 0);
        put('C', 1);
        put('G', 2);
        put('T', 3);
    }};

    public List<String> findRepeatedDnaSequences(String s) {
        List<String> ans = new ArrayList<String>();
        int n = s.length();
        if (n <= L) {
            return ans;
        }
        int x = 0;
        for (int i = 0; i < L - 1; ++i) {//前10位
            x = (x << 2) | bin.get(s.charAt(i));
        }
        Map<Integer, Integer> cnt = new HashMap<Integer, Integer>();
        for (int i = 0; i <= n - L; ++i) {
            x = ((x << 2) | bin.get(s.charAt(i + L - 1))) & ((1 << (L * 2)) - 1);
            cnt.put(x, cnt.getOrDefault(x, 0) + 1);
            if (cnt.get(x) == 2) {
                ans.add(s.substring(i, i + L));
            }
        }
        return ans;
    }
```



# 其他

## ==找规律==

### 96.不同的二叉搜索树

- 给节点数n，求能构成的二叉搜索树种类
- 有点动态规划的意思，但是我更倾向于找规律

```java
    /**
     * 以该点为根节点的个数=左右子树相乘
     * nums[]
     * 1
     * 1    1
     * 2    1   2
     * 5    2   2   5
     * 14   5   4   5   14
     *
     * eachNode[]   要多一个保证eachNode[0]*eachNode[1]有值
     * 1    1    2   5   14  42
     */
    public int numTrees(int n) {
        int []nums=new int[n];
        int []eachNode=new int[n+1];
        nums[0]=1;
        eachNode[0]=1;
        eachNode[1]=1;

        for(int i=1;i<n;i++){
            int sum=Arrays.stream(nums).sum();   //nums[i]作为根节点
            nums[i]=sum;

            for(int j=0;j<i;j++){
                nums[j]=eachNode[j]*eachNode[i-j];   //左右树相乘，并利用对称性
            }
            eachNode[i+1]=Arrays.stream(nums).sum();   //输入为i时的最终结果
        }
        return eachNode[n];
    }
```



## ==迭代==

- 只是遍历，可能用到当前状态前面的值，但是算不上动态规划

### 121.买股票的最佳时机

- 一个所有天数的prices数组，只能先买入再卖出。求最大利润
- 动态规划，第i天卖出最多能赚多少

```java
 public int maxProfit(int[] prices) {
        int result=0;
        int min=prices[0];//记录第i天前的最小值
        for(int num:prices){
            if(num<min){
                min=num;
            }
            if(result<num-min){
                result=num-min;
            }
        }
        return result;
  }
```

### 566.重塑矩阵

- 二维数组 `mat` 表示的 `m x n` 矩阵，重构的矩阵的行数r和列数c。
  - 行遍历顺序填充,不匹配则返回原矩阵
- 一次遍历

```java
 public int[][] matrixReshape(int[][] mat, int r, int c) {
        if(r*c!=mat.length*mat[0].length){
            return mat;
        }
        int [][] res=new int[r][c];
        int r1=0;
        int c1=0;
        for(int i=0;i<mat.length;i++){
            for(int j=0;j<mat[0].length;j++){
                if(c1>=c){//不是 c1>c。因为c是列数
                    c1=0;
                    r1++;
                }
                res[r1][c1++]=mat[i][j];
            }
        }
        return res;
    }
```

### 118.杨辉三角

- 给定一个非负整数 *`numRows`，*生成「杨辉三角」的前 *`numRows`* 行。
- O(n2)。有些算法就是已经到了算法下界，因为每个元素都要赋值

```java
public List<List<Integer>> generate(int numRows) {
        List<List<Integer>>lists=new ArrayList<>();

        for(int j=0;j<numRows;j++){
            List <Integer>list=new ArrayList<>();
            for(int i=0;i<=j;i++){
                if(i==0||i==j){
                    list.add(1);
                }
                else {
                    list.add(lists.get(j - 1).get(i - 1) + lists.get(j - 1).get(i));
                }
            }
            lists.add(list);
        }
        return lists;
    }
```

## ==String==

### 05.替换空格(剑指)

- StringBuilder

```java
class Solution {
    public String replaceSpace(String s) {
        if(s==null){
            return null;
        }

        //遇到' '则追加"&20"
        String str="%20";
        StringBuilder strb=new StringBuilder();
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)!=' '){
                strb.append(s.charAt(i));
            }else{
                strb.append(str);
            }
        }

        return strb.toString();
    }
}
```

### 58.左旋转字符串(剑指)

- 字符串的左旋转操作是把字符串前面的若干个字符转移到字符串的尾部。请定义一个函数实现字符串左旋转操作的功能。比如，输入字符串"abcdefg"和数字2，该函数将返回左旋转两位得到的结果"cdefgab"。
- 能用java自带方法就用，代码越少越ok。

```java
class Solution {
    public String reverseLeftWords(String s, int n) {
        if(s==null){
            return null;
        }

        /*
        只是字符串拼接可以不用StringBuilder
        StringBuilder strb=new StringBuilder(s.substring(n,s.length()));
        strb.append(s.substring(0,n));

        return strb.toString();
        */

        //剪切+拼接
        return s.substring(n,s.length())+s.substring(0,n);
    }
}
```

### 3.无重复字符的最长字串

- 给定一个字符串 `s` ，请你找出其中不含有重复字符的 最长子串 的长度。

```java
	/*
    st记录当前位置能回溯到的最前位置
    HashMap记录是否重复
    不断更新st和max
     */
public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer>map=new HashMap<>();
        int st=0;           //第i个位置前最大不重复串的起始
        int max=0;
        for(int i=0;i<s.length();i++){
            char c=s.charAt(i);
            if(!map.containsKey(c)){
                map.put(c,i);
                max=Math.max(max,i-st+1);
            }else {
                st=Math.max(map.get(c)+1,st);//更新为最大可用值
                map.put(c,i);
                max=Math.max(max,i-st+1);   //。要加上，否则"tmmzuxt"最后的t会没被算
            }
        }
        return max;
    }
```

### 5.最长回文字串

- 中心扩展

```java
public String longestPalindrome(String s) {
        int st=0;
        int end=0;//不包括end
        int max=1;
        int i=0;
        while (i<s.length()){
            int k=i;
            int j=i;

            //找到重复字符的开始和结尾
            while (j>0 && s.charAt(j-1)==s.charAt(i)){
                j--;
            }
            while (k<s.length()-1 && s.charAt(k+1)==s.charAt(i)){
                k++;
            }

            while (j-1>=0 && k+1<=s.length()-1){
                if(s.charAt(j-1)==s.charAt(k+1)){
                    j--;
                    k++;
                }else {
                    break;
                }
            }

            int len=k-j+1;
            if(len>max){
                st=j;
                end=k;
                max=len;
            }
            i++;
        }

        return s.substring(st,end+1);
    }
```

### 557.反转字符串中的单词

- 给定一个字符串 `s` ，你需要反转字符串中每个单词的字符顺序，同时仍保留空格和单词的初始顺序。
- 一步步处理，直到满足要求

```java
public String reverseWords(String s) {
        int start=0;
        int end=s.length()-1;

        //不计算前后空格
        while (start<=end && s.charAt(start)==' ' ){
            start++;
        }
        while ( start<=end  && s.charAt(end)==' '){
            end--;
        }

        StringBuilder res=new StringBuilder();
        int index=start;//记录每个单词开头
        for(int i=start;i<=end;i++){
            if(s.charAt(i)==' ' || i==end){//找到了单词的结尾
                //存进res
                int j=i;
                if(s.charAt(i)==' '){//处理空格
                    j--;
                }
                while (j>=index){//反转
                    res.append(s.charAt(j--));
                }
                if(i!=end){
                    res.append(' ');//处理空格
                }

                //找到下个单词的开头
                index=i;
                while (s.charAt(index)==' '){
                    index++;
                }
            }
        }
        return res.toString();
    }
```

### 6.Z字形变换

- 给字符串，进行Z字形排列

- 直接排

```java
    public String convert(String s, int numRows) {
        StringBuilder res=new StringBuilder();
        //可改用char[][]
        StringBuilder[]list=new StringBuilder[numRows];
        for(int i=0;i<numRows;i++){
            list[i]=new StringBuilder();
        }

        int i=0;
        int rowIndex=0;//存放的是哪一列
        boolean isDown=true;//向上或向下
        while (i<s.length()){

            list[rowIndex].append(s.charAt(i));
            if(!isDown){//向上时每行加空格
                for(int j=0;j<rowIndex;j++){
                    if(i!=j){
                        list[j].append(" ");
                    }
                }
            }

            //改变方向
            if(rowIndex==numRows-1)
                isDown=false;
            if(rowIndex==0)
                isDown=true;
            //防止越界，为了处理AB 1
            if(isDown && rowIndex!=numRows-1)
                rowIndex++;
            if(!isDown && rowIndex!=0)
                rowIndex--;
            i++;
        }

        //从左到右，再从上到下遍历
        for(StringBuilder strb:list){
            for(int k=0;k<strb.length();k++){
                if(strb.charAt(k)!=' '){
                    res.append(strb.charAt(k));
                }
            }
        }
        return res.toString();
    }
```

- 数学周期函数

### 12.整数转罗马数

- 每一位列举十个

```java
    public String intToRoman(int num) {
        String[][]strs={
                //加个0为了处理余数为0
                {"","I","II","III","IV","V","VI","VII","VIII","IX"},
                {"","X","XX","XXX","XL","L","LX","LXX","LXXX","XC"},
                {"","C","CC","CCC","CD","D","DC","DCC","DCCC","CM"},
                {"","M","MM","MMM"}
        };
        StringBuilder res=new StringBuilder();
        int i=0;
        while (num>0){
            int remainder=num%10;
            num/=10;
            res.insert(0,strs[i++][remainder]);//从前面插入
        }
        return res.toString();
    }
```

- 只列举不重复的

```java
    public String intToRoman(int num) {
        //直列举不重复的基本单元
        int []values={1000,900,500,400,100,90,50,40,10,9,5,4,1};
        String[]symbols= {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
        StringBuilder res=new StringBuilder();
        for(int i=0;i<values.length;i++){
            //1,10的倍数可以累加，其他的一减就没了
            while (num>=values[i]){
                num-=values[i];
                res.append(symbols[i]);
            }

        }
        return res.toString();
    }
```

### 14.最长公共前缀

- 纵向扫描，res长度为0一个个增加

```java
    public String longestCommonPrefix(String[] strs) {
        StringBuilder res=new StringBuilder();
        for(int i=1;i<strs[0].length()+1;i++){
            //每次拿一个字符也行，然后记录下能拿多少个
            StringBuilder strb=new StringBuilder(strs[0].substring(0,i));
            boolean isAllContains=true;
            for (int j=1;j<strs.length;j++){
                //首先i不能越界
                if(i>strs[j].length() ||!strs[j].substring(0,i).contains(strb)) {
                    isAllContains = false;
                    break;
                }
            }
            if(isAllContains){
                res=strb;
            }else {
                return res.toString();
            }
        }
        return res.toString();
    }
```

- 还有横向扫描也行，先找第一个和第二个的前缀，然后和第三个比，更新前缀......不断更新

### 28.实现strStr()

- 找子串位置
- 遍历

```java
    public int strStr(String haystack, String needle) {
        if(needle==null){
            return 0;
        }
        for(int i=0;i<=haystack.length()-needle.length();i++){
            if(haystack.substring(i,i+needle.length()).equals(needle)){
                return i;
            }
        }
        return -1;
    }
```

- KMP。。。

### 38.外观数列

- 「外观数列」是一个整数序列，从数字 1 开始，序列中的每一项都是对前一项的描述
- 递归并遍历。。

```java
    public String countAndSay(int n) {
        List<StringBuffer> res=new ArrayList<>();
        res.add(new StringBuffer("1"));

        for (int i=1;i<n;i++) {
            StringBuffer strb=res.get(i-1);
            StringBuffer next=new StringBuffer();
            char c=strb.charAt(0);
            int j=0;
            int count=0;
            while (j<strb.length()){
                if(strb.charAt(j)==c){
                    count++;
                }
                if(strb.charAt(j)!=c){
                    next.append(count).append(c);
                    count=1;
                    c=strb.charAt(j);
                }
                j++;
            }
            next.append(count).append(c);
            res.add(next);
        }
        return res.get(n-1).toString();
    }
```

### 56.最后一个单词长度

- 遍历

```java
    public int lengthOfLastWord(String s) {
        int res=0;
        int index=s.length()-1;
        while (!(s.charAt(index)>='a'&& s.charAt(index)<='z' || s.charAt(index)>='A'&&s.charAt(index)<='Z'))         {
            index--;
        }
        for(int i=index;i>=0;i--){
            if(s.charAt(i)>='a'&& s.charAt(i)<='z' || s.charAt(i)>='A'&&s.charAt(i)<='Z'){
                res++;
            }else {
                break;
            }
        }
        return res;
    }
```

### 125.验证回文字符串

- 如果在将所有大写字符转换为小写字符、并移除所有非字母数字字符之后，短语正着读和反着读都一样。则可以认为该短语是一个 **回文串** 
- 双指针逐一比较（没有使用额外空间

```java
    public boolean isPalindrome(String s) {
        int left=0;
        int right=s.length()-1;

        while (left<=right){
            //当left==right 就会退出
            while (left<right && !Character.isDigit(s.charAt(left)) && !Character.isAlphabetic(s.charAt(left))){
                left++;
            }
            char leftChar = Character.isUpperCase(s.charAt(left))?Character.toLowerCase(s.charAt(left)):s.charAt(left);

            while (right>left && !Character.isDigit(s.charAt(right)) && !Character.isAlphabetic(s.charAt(right))){
                right--;
            }
            char rightChar = Character.isUpperCase(s.charAt(right))?Character.toLowerCase(s.charAt(right)):s.charAt(right);
            if(leftChar!=rightChar){
                return false;
            }
            right--;
            left++;
        }
        return true;
    }
```

## ==数组处理==

### 48.旋转图像

- 给定一个 *n* × *n* 的二维矩阵 `matrix` 表示一个图像。请你将图像顺时针旋转 90 度

````java
//由外向内旋转    
public void rotate(int[][] matrix) {
        int len=matrix.length;
        int i=0;
        int n=len;
        while (n>1) {
            getMatrix(matrix,n,i,len-i-1);
            n-=2;//大小减2
            i+=1;
        }
    }

    private void getMatrix(int [][]matrix,int n,int st,int end){
        int []nums1=new int[n-1];
        int []nums2=new int[n-1];

        int index=0;
        for(int i=st;i<end;i++){//上->右
            nums2[index++]=matrix[i][end];//右（临时保存
            matrix[i][end]=matrix[st][i];
        }

        index=n-2;
        for(int i=st+1;i<=end;i++){
            nums1[index]=matrix[end][i];//下
            matrix[end][i]=nums2[index--];
        }

        index=n-2;
        for(int i=st+1;i<=end;i++){
            nums2[index]=matrix[i][st];//左
            matrix[i][st]=nums1[index--];
        }

        index=0;
        for(int i=st;i<end;i++){
            matrix[st][i]=nums2[index++];
        }
    }
````

- 官方
  - 先上下翻转，再对角互换。matrix(new)[col] [n−row−1]=matrix[row] [col]

```java
public void rotate(int[][] matrix) {
    int n=matrix.length;

    for(int i=0;i<n/2;i++){
        for(int j=0;j<n;j++){
            int temp=matrix[i][j];
            matrix[i][j]=matrix[n-1-i][j];
            matrix[n-1-i][j]=temp;
        }
    }

    for(int i=0;i<n;i++){
        for(int j=i;j<n;j++){
            int temp=matrix[i][j];
            matrix[i][j]=matrix[j][i];
            matrix[j][i]=temp;
        }
    }
}
```

### 75.颜色分类

- 给定一个包含红色、白色和蓝色、共 n 个元素的数组 nums ，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。必须在不使用库的sort函数的情况下解决这个问题。
- 分别记录当前0、1、2该插入的位置   

```java
public void sortColors(int[] nums) {
        int x=0;//0的当前末尾
        int y=0;//1
        int z=0;//2
        int n=nums.length;
        for(int i=0;i<n;i++){
            if(nums[i]==0){
                swap(nums,x,i);
                x++;y++;z++;  //一看就懂，不再注释
            }
            else if(nums[i]==1){
                swap(nums,y,i);
                y++;z++;
            }else {
                swap(nums,z,i);
                z++;
            }
        }
    }
	//整体向后移动。感觉不是很好
    private void swap(int[]nums,int st,int end){
        int tmp=nums[end];
        while (end>st){
            nums[end]=nums[end-1];
            end--;
        }
        nums[st]=tmp;
    }
```

- 0交换到开头，2交换到结尾。只做交换，不做后移

```java
public void sortColors(int[] nums) {
        int x=0;//0的当前位置
        int y=nums.length-1;//2当前位置
        int n=nums.length;
        for(int i=0;i<n;i++){  //先确定0
            if(nums[i]==0&&i!=x){
                swap(nums,i,x);
                x++;
                if(i!=0)
                    i--;//换回来的还是0能继续换
            }
        }
        for(int i=n-1;i>=0;i--){
            if(nums[i]==2&&i!=y){
                swap(nums,i,y);
                y--;
                if(i!=n-1)
                    i++;//若换回来还是2则能继续换
            }
        }
    
    }
    private void swap(int []nums,int i,int j){
        int tmp=nums[i];
        nums[i]=nums[j];
        nums[j]=tmp;
    }
```

### 977.有序数组的平方

- 别轻易改变原引用指向的值，除非特别说明。返回的值，该创建新的就创建
- 找不到小的，可以先放大的

```java
 public int[] sortedSquares(int[] nums) {
        int []res=new int[nums.length];//改变原来的数组反而更加复杂

        int i=0;
        int j=nums.length-1;
        int index=j;
        while(i<=j){
           if(nums[j]*nums[j]>nums[i]*nums[i]){
               res[index--]=nums[j]*nums[j];//头尾比较，先放大的
               j--;
           }else {
               res[index--]=nums[i]*nums[i];
               i++;
           }

        }
        return  res;
}
```

### 189.轮转数组

- 给你一个数组，将数组中的元素向右轮转 `k` 个位置，其中 `k` 是非负数。
- 观察结果的结构,题目的讲解有误导性，能批处理就用，尽量不要一次处理一个
- 有时候太过复杂的方法、有太多限制的方法可以直接抛弃了。

```java
public void rotate(int[] nums, int k) {
        k%=nums.length;
        reverse(nums,0,nums.length-1);//整体倒放
        reverse(nums,0,k-1);//倒放前k个
        reverse(nums,k,nums.length-1);//后面的也倒放
    }
    public void reverse(int []arr,int start,int end){
        while (start<end){
            int temp=arr[start];
            arr[start]=arr[end];
            arr[end]=temp;
            end--;
            start++;
        }
    }
```

### 283.移动零

- 给定一个数组 `nums`，编写一个函数将所有 `0` 移动到数组的末尾，同时保持非零元素的相对顺序。
- 建议考虑好所有特殊情况再提交

```java
public void moveZeroes(int[] nums) {
        int count=nums[0]!=0?1:0;//当前非0数应该放的位置
        for(int i=1;i<nums.length;i++){
            if(nums[i]!=0){
                if(count!=i){     //count和i之间有0
                    nums[count]=nums[i];//交换
                    nums[i]=0;
                }
               count++;
            }
        }
}//小小的程序竟然多次提交出错。。
```

### 54.螺旋矩阵

- 给你一个 `m` 行 `n` 列的矩阵 `matrix` ，请按照 顺时针螺旋顺序，返回矩阵中的所有元素

```java
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer>res=new ArrayList<>();
        int x=matrix.length-1;
        int y=matrix[0].length-1;
        int i=0;
        int j=0;
        while (i<=x && j<=y) {
            order(res,matrix,i++,j++,x--,y--);
        }
        return res;
    }

    //m,n是左上角，x，y是右下角
    private void order(List<Integer>res,int[][] matrix,int m,int n,int x,int y){

        for(int i=n;i<=y;i++){
            res.add(matrix[m][i]);
        }
        for (int i=m+1;i<x;i++){
            res.add(matrix[i][y]);
        }

        if(m!=x){
            for(int i=y;i>=n;i--){
                res.add(matrix[x][i]);
            }
        }
        if(n!=y){//不重叠
            for(int i=x-1;i>=m+1;i--){
                res.add(matrix[i][n]);
            }
        }
    }
```

### 59. 螺旋矩阵 II

- 给你一个正整数 `n` ，生成一个包含 `1` 到 `n2` 所有元素，且元素按顺时针顺序螺旋排列的 `n x n` 正方形矩阵 `matrix`
- 按层来填

```java
    int [][]res;
    int num=1;
    public int[][] generateMatrix(int n) {
        res=new int[n][n];
        int i=0;
        int j=n-1;
        while (i<=j){
            round(i,i,j,j);
            i++;
            j--;
        }
        return res;
    }

    //左上右下，当前数字
    private void round(int x, int y, int m, int n) {
        for(int i=y;i<=n;i++){
            res[x][i]=num++;
        }
        for(int i=x+1;i<=m-1;i++){
            res[i][n]=num++;
        }

        if(x!=m){
            for(int i=n;i>=y;i--){
                res[m][i]=num++;
            }
        }
        if(y!=n){
            for(int i=m-1;i>=x+1;i--){
                res[i][y]=num++;
            }
        }
    }
```

### 66.加一

- 给定一个由 整数 组成的 非空 数组所表示的非负整数，在该数的基础上加一。最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。你可以假设除了整数 0 之外，这个整数不会以零开头

```java
    public int[] plusOne(int[] digits) {
        boolean isAllNine=true;
        for(int digit:digits){
            if(digit!=9){
                isAllNine=false;
                break;
            }
        }
        if(isAllNine){
            //全9
            int[]result=new int[digits.length+1];
            result[0]=1;
            return result;
        }else {
            for(int i=digits.length-1;i>=0;i--){
                if(digits[i]==9){
                    digits[i]=0;
                }else {
                    //第一个不为9的数加一
                    digits[i]=digits[i]+1;
                    break;
                }
            }
            return digits;
        }
    }
```

### 136.只出现一次的数字

- 给你一个 **非空** 整数数组 `nums` ，除了某个元素只出现一次以外，其余每个元素均出现两次
- 异或

```java
    //异或    相同为0
    //位运算处理的是二进制位，同时可以映射到十进制
    public int singleNumber(int[] nums) {
        int result=nums[0];
        for(int i=1;i<nums.length;i++){
            result^=nums[i];
        }
        return result;
    }
```

### 37.只出现一次的数字二

- 给你一个整数数组 `nums` ，除某个元素仅出现 **一次** 外，其余每个元素都恰出现 **三次 。**请你找出并返回那个只出现了一次的元素
- 余数法，每位相加后除以3的余数

```java
    //一位位算
    //所有出现三次的数的每位和加起来是3的倍数，目标总的余数
    public int singleNumber(int[] nums) {
        int res=0;
        int base=1;
        for(int i=0;i<32;i++){
            int total=0;
            for(int n:nums){
                //验证第i位是否是0 ，且不改变n
                total+=((n&(base<<i))==0)?0:1;
            }
            //跟第i位或
            res|=total%3<<i;
        }
        return res;
    }
```



## ==数字处理==

### 7.整数反转

- x是32位有符号数，反转。若超出int范围则返回0，假设计算机不存储64位。(-120->-21)

```java
    public int reverse(int x) {
        int res=0;
        int symbol;//处理符号，为了后面范围判断
        if(x<0){ 
            x=-x;
            symbol=-1;
        }else {
            symbol=1;
        }

        while (x>0){
            int remainder=x%10;//余数
            x/=10;

            //超出范围返回0,答案的没有我的严谨。。
            if((res>Integer.MAX_VALUE/10 )||
            (symbol>0 && res==Integer.MAX_VALUE/10 && remainder>=7)||
                    (symbol<0 && res==Integer.MAX_VALUE/10 && remainder>=8)
                   ){
                return 0;
            }

            res=res*10+remainder;
        }
        return res*symbol;
    }
```

### 9.回文数

- 0->0      -121->121-  

```java
    public boolean isPalindrome(int x) {
        if(x<0){
            return false;
        }
        int res=0;
        int n=x;
        while (n>0){
            int remainder=n%10;
            n/=10;
            res=res*10+remainder;
        }
        return res==x;
    }
```



## ==数学==

### 6.Z字形变换

- 给字符串，进行Z字形排列
- 周期性。总周期是2*numRows-2,但中间行还有第二个字符

```java
    public String convert(String s, int numRows) {
        StringBuilder res=new StringBuilder();
        int cycle=numRows!=1?2*numRows-2:1;   //周期  numRows=1时是1
        for(int i=0;i<numRows;i++){
            int j=i;
            while (j<s.length()){
                res.append(s.charAt(j));
                if(i!=0 && i!=numRows-1){//中间行都有第二个字符
                    int k=(j/cycle+1)*cycle-i;//自己算
                    if(k<s.length())
                        res.append(s.charAt(k));
                }
                j+=cycle;    //每次加一周期
            }

        }
        return res.toString();
    }
```

### 62.不同路径

- 向下或向右。m*n格，有多少条不同路径到达右下角
- 排列组合。一共走m+n-2步，其中m-1步是向下。C(m+n-2)(m-1).从m+n-2中选m-1个。组合问题

```java
public int uniquePaths(int m, int n) {
    long ans = 1;//防止越界
    int x=n;
    for (int y = 1; y < m;  y++) {//目前就这样写才不越界
        ans = ans * x / y;
        x++;
    }
    return (int) ans;
}
```

### 67.二进制求和

- 模拟

```java
    public String addBinary(String a, String b) {
        StringBuilder strb=new StringBuilder();
        int carry=0;//进位
        int maxLen=Math.max(a.length(),b.length());
        int m=a.length()-1;
        int n=b.length()-1;
        for(int i=0;i<maxLen;i++){
            if(i!=a.length() && i!=b.length()){
                int sum=(a.charAt(m-i)-'0')+(b.charAt(n-i)-'0')+carry;//情况0，1，2，3
                strb.insert(0,sum%2);
                carry=sum>1?1:0;
            }else {
                if(i==a.length()){//a长度较短
                    for(int j=i;j<b.length();j++){
                        int sum=(b.charAt(n-j)-'0')+carry;//情况0，1，2，3
                        strb.insert(0,sum%2);
                        carry=sum>1?1:0;
                    }
                }else {
                    for(int j=i;j<a.length();j++){
                        int sum=(a.charAt(m-j)-'0')+carry;//情况0，1，2，3
                        strb.insert(0,sum%2);
                        carry=sum>1?1:0;
                    }
                }
                break;
            }
        }
        if(carry==1){//还有进位
            strb.insert(0,1);
        }
        return strb.toString();
    }
```

- 官方
  - carry每次权值减半，进位

```java
class Solution {
    public String addBinary(String a, String b) {
        StringBuffer ans = new StringBuffer();

        int n = Math.max(a.length(), b.length()), carry = 0;
        for (int i = 0; i < n; ++i) {
            carry += i < a.length() ? (a.charAt(a.length() - 1 - i) - '0') : 0;
            carry += i < b.length() ? (b.charAt(b.length() - 1 - i) - '0') : 0;
            ans.append((char) (carry % 2 + '0'));
            carry /= 2;
        }

        if (carry > 0) {
            ans.append('1');
        }
        ans.reverse();

        return ans.toString();
    }
}
```

### 69.x的平方根

- 给你一个非负整数 `x` ，计算并返回 `x` 的 **算术平方根**。不允许使用任何内置指数函数和算符，例如 `pow(x, 0.5)` 或者 `x ** 0.5` 

- 二分法

```java
    public int mySqrt(int x) {
        int left=0;
        int right=x;
        int result=0;
        while (left<=right){
            int mid=(left+right)/2;
            long product=(long)mid*mid;
            if(product==x || product<x && (long)(mid+1)*(mid+1)>x){
                result=mid;
                break;
            }
            if(product<x){
                left=mid+1;
            }else {
                right=mid-1;
            }
        }
        return result;
    }
```

### 119.杨辉三角

- 给定一个非负索引 `rowIndex`，返回「杨辉三角」的第 `rowIndex` 行。在「杨辉三角」中，每个数是它左上方和右上方的数的和
- 不使用额外 空间

```java
    public List<Integer> getRow(int rowIndex) {
        List<Integer>list=new ArrayList<>();
        list.add(1);
        for(int i=1;i<=rowIndex;i++){
            int pre=list.get(0);
            for(int j=1;j<list.size();j++){
                int now=list.get(j);
                list.set(j,list.get(j)+pre);
                pre=now;
            }
            list.add(1);
        }
        return list;
    }
```





## ==处理( x , y  )节点==

### 56.合并区间

- 以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。请你合并所有重叠的区间，并返回 一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间 。

```java
    //按照左端点进行排序。空间更少
    public int[][] merge(int[][] intervals) {
        List<Integer[]> list=new ArrayList<>();
        for (int[] interval : intervals) {
            list.add(new Integer[]{interval[0], interval[1]});
        }
        list.sort((x,y)->x[0]-y[0]);

        List<Integer[]>res=new ArrayList<>();
        list.forEach(node -> {
            if(res.size()==0){
                res.add(node);
            }else {
                Integer[] lastNode =res.get(res.size()-1);
                if(node[0]<=lastNode[1]){
                    Integer []newNode=new Integer[]{lastNode[0],Math.max(node[1],lastNode[1])};
                    res.remove(res.size()-1);
                    res.add(newNode);
                }else {
                    res.add(node);
                }
            }
        });
        int [][]result = new int[res.size()][2];
        for(int i=0;i<res.size();i++){
            result[i][0]=res.get(i)[0];
            result[i][1]=res.get(i)[1];
        }
        return result;
    }



    //官方，不用先转为list。时间更少，内存用得反而多了？
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals,(x,y)->x[0]-y[0]);
        List<int []>res=new ArrayList<>();
        for (int[] interval : intervals) {
            if(res.size()==0){
                res.add(new int[]{interval[0],interval[1]});
            }else {
                int[] lastNode =res.get(res.size()-1);
                if(interval[0]<=lastNode[1]){
                    int []newNode=new int[]{lastNode[0],Math.max(interval[1],lastNode[1])};
                    res.remove(res.size()-1);
                    res.add(newNode);
                }else {
                    res.add(new int []{interval[0],interval[1]});
                }
            }
        }
        return res.toArray(new int[res.size()][]);
    }
```

### 57.插入区间

- 给你一个 **无重叠的** *，*按照区间起始端点排序的区间列表。在列表中插入一个新的区间，你需要确保列表中的区间仍然有序且不重叠（如果有必要的话，可以合并区间）
- 用56的

```java
    //按照左端点进行排序。空间更少
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<Integer[]> list=new ArrayList<>();
        for (int[] interval : intervals) {
            list.add(new Integer[]{interval[0], interval[1]});
        }
        list.add(new Integer[]{newInterval[0],newInterval[1]});
        list.sort((x,y)->x[0]-y[0]);

        List<Integer[]>res=new ArrayList<>();
        list.forEach(node -> {
            if(res.size()==0){
                res.add(node);
            }else {
                Integer[] lastNode =res.get(res.size()-1);
                if(node[0]<=lastNode[1]){
                    Integer []newNode=new Integer[]{lastNode[0],Math.max(node[1],lastNode[1])};
                    res.remove(res.size()-1);
                    res.add(newNode);
                }else {
                    res.add(node);
                }
            }
        });
        int [][]result = new int[res.size()][2];
        for(int i=0;i<res.size();i++){
            result[i][0]=res.get(i)[0];
            result[i][1]=res.get(i)[1];
        }
        return result;
    }
```

- 官方
  - 只考虑少量的特殊情况，其他的通通重叠

```java
    //按照左端点进行排序。空间更少
    public int[][] insert(int[][] intervals, int[] newInterval) {
        int left=newInterval[0];
        int right=newInterval[1];
        List<int []>list=new ArrayList<>();

        boolean isplaced = false;

        for(int [] node:intervals){
            //新节点在左
            if(node[0]>right){
                if(!isplaced){
                    list.add(new int[]{left,right});
                    isplaced=true;
                }
                list.add(node);
            }else if(node[1]<left){
                list.add(node);
            }else {//重叠
                left=Math.min(left,node[0]);
                right=Math.max(right,node[1]);
            }
        }
        if(!isplaced){
            list.add(new int[]{left,right});
        }
        return list.toArray(new int[list.size()][]);
    }
```



## ==真其他==

### 幽谷祝祀

- 原神新圣遗物“来歆余响”触发概率
- 每一次都受前面触发的影响

```java
/**
     * 如n=3。算的是nums[3]的值
     * 下标  3      2      1    0
     *     0.36*0.528                   //在第三次触发tmp1
     *     0.56*0.472*0.488             //第二tmp1
     *     0.76*0.472*0.512*0.36        //第一tmp1
     *     0.96*0.472*0.512*0.64        //一次都没有tmp2
     * 由于p只能0.36,0.56,0.76,0.96,1。所以当前概率最多与前四次相关。
     * 与前面倒数第五次以及之前是否触发无关
     * 结果约为0.5240898176437716   每次都有点不同
     */
    public static void main(String[] args) {
        double p=0.36;//初始概率
        double []nums=new double[15];

        for(int i=0;i<15;i++){
            nums[i]=result(nums,p,i);
        }
        System.out.println(Arrays.toString(nums));

    }


    public static  double result(double []nums,double p,int n){
        if(n==0){
            nums[0]=p;//第一次是0.36
            return nums[0];
        }
        double sum=0;//总概率

        //至少触发了一次
        int i=0;
        while (i<n){
            if(p>1){
                break;
            }
            int j=n-1;
            double tmp1=1;
            while (j>=n-i){
                tmp1*=(1-nums[j]);//不触发的那些
                j--;
            }
            tmp1=tmp1*p*nums[n-i-1];//触发的那次
            sum+=tmp1;
            p+=0.2;
            i++;
        }

        //一次都没有触发
        double tmp2=1;
        if(p>1){//p累加到1说明最后一次为前面4次都没有触发。
            tmp2=1*tmp2*(1-nums[n-1])*(1-nums[n-2])*(1-nums[n-3])*(1-nums[n-4]);
        }else {
            for(double num:nums){
                tmp2*=(1-num);
            }
            tmp2*=p;
        }
        sum=sum+tmp2;
        return sum;
    }
```

### 31.下一个排列

- 整数数组表示一个数，排列是数组元素打乱顺序，下一个排列是下个比它大的数，若已经最大则返回其最小的值，必须原地修改

```java
//例如：（过程）1234753->1235743->1235347
//只能说和官方思路一致，尽量只改变后面的位
public void nextPermutation(int[] nums) {
        int len=nums.length;
        boolean isFind=false;
        int mi=nums[len-1];
        int res=len-1;

        for(int i=len-1;i>=0;i--){//从后向前方向第一个非递升数的下标res
            if(nums[i]<mi){
                res=i;
                isFind=true;
                break;
            }else {
                mi=nums[i];
            }
        }
        if(!isFind){//没找到，说明是降序，直接反过来
            for(int i=0;i<len/2;i++)
                swap(nums,i,len-1-i);
        }
        else {
            int i=res+1;
            while (i<len){//从res+1开始找第一个比nums[res]小或等于的数下标i
                if(nums[i]<=nums[res])
                    break;
                i++;
            }
            swap(nums,res,i-1);//跟i前一个数交换
            int k=0;
            for(int j=res+1;j<(len+res+1)/2;j++){//把res+1及之后 的变成升序（最小化）
                swap(nums,j,len-1-k);
                k++;
            }
        }

    }
    private void swap(int []nums,int i,int j){
        int temp=nums[i];
        nums[i]=nums[j];
        nums[j]=temp;
    }
```

### Disjoint Set

- 并查集,用一棵树来表示一个集合，检查时看根节点一致则在同一个集合
- 检查图上有没有环，新的边的2个点都在同一个集合，则存在环
- 并查集处理一维序列比较好，二维的话还是dfs吧

```java
    //是无向有环图则返回true
    public boolean loopGraph(){
        int [][]edges={
                {0,1},{1,2},{1,3},{2,5},{3,4},{2,4}
        };
        int []parents=new int[6];
        Arrays.fill(parents,-1);//初始化为-1

        for(int i=0;i<edges.length;i++){
            int []parx=findParent(edges,parents,i,0);

            int []pary=findParent(edges,parents,i,1);
            if(parx[0]==pary[0]){//同一根节点
                return true;
            }
            /*
            根据当前节点到树的根节点粗略估计树的高度
            高的作为新的根
             */
            if(parx[1]>pary[1]){
                parents[pary[0]]=parx[0];
            }else {
                parents[pary[0]]=parx[0];
            }

        }
        return false;
    }

    private int[] findParent(int [][]edges,int []parents,int i,int j){
        int point=edges[i][j];
        int pre=point;//保存当前节点信息
        int root=parents[point];
        int high=0;
        while (root!=-1){// 根节点是-1
            pre=root;
            root=parents[root];
            high++;
        }
        return new int []{pre,high};
    }
```



# 原则

## 忌

- （387）能用基本类型int[]就不用引用类型HashMap

## 要

- （6，62）就是说，有时候会用到数学知识

## 特殊

- 打表法应该是最快的