public class 字符串比较和包含 {
    public static void main(String[] args) {
        String str1="jkk";
        String str2="ajkk";
        String str3="jkk";

        //比较
        System.out.println(str1.equals(str2));
        System.out.println(str1.equals(str3));

        //包含
        System.out.println(str2.contains(str1));
    }

}
