public class 依照模式匹配替换和分隔 {
    public static void main(String[] args) {
        //regex是正则表达式,具体的可以查看书中的附录H

        String s="Welcome to java";

        //匹配
        System.out.println(s.matches(".*come.*"));//true

        //替换
        System.out.println(s.replaceAll("[eo]","A"));

    }
}
