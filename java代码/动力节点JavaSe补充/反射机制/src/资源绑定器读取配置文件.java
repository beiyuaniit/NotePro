import java.util.ResourceBundle;

public class 资源绑定器读取配置文件 {
    public static void main(String[] args) {
        /**
         * 只绑定类路径下.propeties文件
         * 写路径时不用后缀
         * 目前最简单的方法了
         */
        ResourceBundle bundle=ResourceBundle.getBundle("class");
        String className=bundle.getString("className");

        System.out.println(className);
    }
}
