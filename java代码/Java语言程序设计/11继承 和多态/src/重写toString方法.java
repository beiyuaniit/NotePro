public class 重写toString方法 {
    public static void main(String[] args) {
        //因为Object类的toString方法没多大用
    }
}

class E{
    int a;

    @Override
    public String toString() {
        return "E{" +
                "a=" + a +
                '}';
    }
}
