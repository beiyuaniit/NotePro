public class 字符串的替换和分隔 {
    public static void main(String[] args) {
        String s="Welcome to javaee";

        //替换单个字符
        System.out.println(s.replace('e','E'));

        //替换字符串
        System.out.println(s.replaceAll("come","okkk"));

        //分隔，返回字符串数组,不包括分割的字符串
        String []a=new String[20];
        a=s.split("e");
        for(String e:a){
            System.out.println(e);
        }
    }
}
