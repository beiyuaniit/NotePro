package OneToFiveChapter;

/**
 * @author: beiyuan
 * @className: final修饰成员变量06
 * @date: 2022/3/5  21:45
 */
public class final修饰成员变量06 {
    public final String name;

    /*
    构造函数执行后必须赋值，且不能再更改
     */
    public final修饰成员变量06(String name) {
        this.name = name;
    }

//    public void setName(){
//        name="Kitty";
//    }
}
