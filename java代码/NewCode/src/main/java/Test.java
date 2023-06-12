/**
 * @author: beiyuan
 * @date: 2022/11/27  15:26
 */
public class Test {
    static int a;
    int b;
    static int c;

    public int aMethod() {
        a++;
        return a;
    }

    public int bMethod() {
        b++;
        return b;
    }

    public static int cMethod() {
        c++;
        return c;
    }

    public static void main(String[] args) {
        String str="";
        String str1=str.concat("abc");
        String str2=str1.concat("123");
        System.out.println(str);
        System.out.println(str1);
        System.out.println(str2);
    }
}
