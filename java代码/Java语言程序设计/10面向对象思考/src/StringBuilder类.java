public class StringBuilder类 {
    public static void main(String[] args) {
        //可变的字符串
        StringBuilder s=new StringBuilder("Welcom to jav");

        //末尾追加
        s.append('a');
        System.out.println(s);

        //删除
        s.delete(0,1);
        System.out.println(s);

        //添加
        s.insert(0,"We");
        System.out.println(s);

        //替换
        s.replace(0,1,"At");
        System.out.println(s);
        s.setCharAt(0,'Q');
        System.out.println(s);

        //倒置
        s.reverse();
        System.out.println(s);

    }
}
