## 数据结构

- 从JavaSe.md中

```java
/*
Collection接口
	集合是一个容器，存放对象的引用(基本类型会自动装箱
	集合用数据结构实现
	存放的是Object类型引用且同一个集合可以存放不同的引用类型
Collection的子接口
	Set接口。

 */

/*
Vector：动态括容数据，线程安全，效率低点
ArrayList：动态扩容数组，线程不安全，效率高点
LinkedList：双向链表，线程不安全

Stack：栈，Vector子类
Queue:队列
PriorityQueue:优先队列
              存放引用数据类型要自定义比较器Comparator
HashSet：底层Hash表。存储元素等于HashMap的key部分，无序不重复(只有key，没有value
        不允许重复的值(所以要重写equals和hashCode方法
TreeSet：底层红黑树存储了TreeMap的key部分，有序不重复（只有key，没有value
*/

/*
Map接口
	数据以key-value形式存储
*/

/*
Hashtable:哈希表，线程安全
HashMap：哈希表，线程不安全
        不允许重复的key
        链地址法处理哈希冲突：位桶+链表+红黑树

TreeMap：二叉排序树，按照key排序

Properties：HashMap，线程安全，key和value只能是String		
	没错就是配置文件.properties相关的那个

 */
```



## 基本类型

- int 是10位。21亿，long是19位

## 数组

- 有默认值，不用手动赋值（方法内外都是

```java
boolean []visited=new boolean[3];
System.out.println(visited[0]);//false
int []arr=new int[3];
System.out.println(arr[2]);//0
```

## Integer

- 没啥就是一些api

```java
Integer.parseInt(str);//要记得这个转数字的
```

## String

- 返回的只能是char

```java
public char charAt(int index)
```

## StringBulider

- 速度快点，线程不安全。而StringBuffer线程安全

## Array

- 长度

```java
 for(int i=0;i<nums.length;i++){}//注意不同于string的length().数组是length字段
//或者
for(int i:nums){}//只访问元素的话可以不用直到长度
//填充元素都为-1
Arrays.fill(nums,-1);
    
```

## List

- 动态大小

```java
/*for(int  i=0;i<list.size();i++){//错误写法，size会不断增加
    list.add(new ArrayList<Integer> ());
}*/
/*
for(int e:list){
	list.add(e+1);//for-each也是错的
}
*/

int size=list.size();//得先保存初始得值
for(int i=0;i<size();i++){
    list.add(new ArrayList<Integer>());
}
```

- 增加或删除后，索引马上会改变

```java
//删除前n个元素
  for(int i=0;i<n;i++)
      list.remove(0);//因为索引改变。只能从第0个删除。LinkedList和ArrayList都一样
```

- list的值不能像数组直接=修改

```java
List<Integer>list=new ArrayList<>();
list.add(3);
//list.get(0)=7;  错误
list.set(7);
```

- List转数组

```java
List<Integer>list=new ArrayList<>();
Integer []arr=list.toArray(new Integer[0]);//new Integer[0]传个空的意思下就行

List<int []> ans=new ArrayList<>();    
ans.toArray(new int[0][0]); //传递一个最终类型的空数组

```

- List可以应用sort函数

  - 用的话就用ArrayList吧，用LinkedList感觉不划算

  ```java
  //为空[] 或者[null] 可以排不报错，但是[null,3]之类的不行
  listArr.sort(((o1, o2) ->o1.val-o2.val));
  ```

  

## HashSet

- set.add()

```java
public boolean add(E e)      
set.add(1); //true
set.add(1);//false

if(!set.add(i)){
    //若set不包含i，则添加，否则返回true
    return true;
}
```

## HashMap

- 创建并添加值

```java
Map<Character, String>map=new HashMap<>(){
    {
         put('2',"abc");
         put('3',"def");
         put('4',"ghi");
     }
};
```

- 没有则取默认值
  - 有则加一

```java
default V getOrDefault(Object key, V defaultValue)
map.put(num, map.getOrDefault(num, 0) + 1);

//以下这样是错的
 Map<Integer, List<Integer>> map=new HashMap<>();
 map.put(1,map.getOrDefault(1,new ArrayList<Integer>()).add(1));  //add(1) 返回的是boolean类型

```

- 还能判断value是否存在

```java

containsKey(Object key)
containsValue(Object value)
```

- lambda中不能修改其他变量
  - 报错：error: local variables referenced from a lambda expression must be final or effectively final

```java
     int count=0;
     colorMap.forEach((color,list)->{
            for(int i=1;i<list.size();i++){
                    if(list.get(i)-list.get(i-1)<m || (list.get(i)+m)%n>list.get(i-1)){
                        count++;
                        break;
                    }
           }
     });
```

## Stack

- 纯栈，底层用Vector实现

  ```jav
  Stack<Integer>stack=new Stack<>();
  ```

  

## BitSet

- 位图
- 维护一个long数组，初始只有一个long
- 用位的0和1表示数字是否存在

```java
   BitSet bitSet=new BitSet();
   bitSet.set(3,true); //可从0开始
   System.out.println(bitSet.get(2)); //false
   System.out.println(bitSet.get(3)); //true
```

- 1亿个bit = 12MB左右的内存空间（11.9209289551MB
- 一亿个int=381.47MB

## 其他

### for-each

- 基本类型只能遍历，不能修改。引用类型可通过引用修改对象

```java
int []nums={1,2,3};
//for-each只能遍历，不能修改
for(int n:nums){
    n+=1;
}
System.out.println(Arrays.toString(nums));//[1,2,3]

StringBuilder []str=new StringBuilder[2];
str[0]=new StringBuilder();
str[1]=new StringBuilder();
str[0].append('a');
str[1].append('b');
//for-each只能遍历，不能修改
for(StringBuilder s:str){
     s.append('c');
}
System.out.println(Arrays.toString(str));//[ac, bc]
```

### 原引用

- 引用本身应该是值传递，但是可以通过引用改变对象的值，所以这里没必要

```java
  public ListNode swapPairs(ListNode head) {
 	 ListNode node=head;
  }
```

