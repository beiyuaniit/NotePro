import java.util.Objects;

public class 重写equals和hashCode方法 {
    /*
    ==判断引用的值
    equals判断引用的内容
     */

    //hashCode()用来在散列存储结构中查找对象，减少对equals的应用，提高效率（散列表

}


class H{
    int a;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        H h = (H) o;
        return a == h.a;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a);
    }
}