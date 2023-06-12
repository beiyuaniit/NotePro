public class 实现Comparable接口 implements Comparable<实现Comparable接口>{

    int a;
    @Override
    public int compareTo(实现Comparable接口 o) {
        return this.a-o.a;
    }
}

