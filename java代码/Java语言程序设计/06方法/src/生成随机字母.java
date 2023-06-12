public class 生成随机字母 {
    public static void main(String[] args) {
        //小写
        char a=(char)('a'+Math.random()*26);

        //大写
        char b=(char)('A'+Math.random()*26);

        System.out.println(a);
        System.out.println(b);
    }
}
