package aop.fifthVersion;

/**
 * @author: beiyuan
 * @className: Service
 * @date: 2022/6/29  16:32
 */
//规定业务功能
public interface Service {
    void buy();
    default void show(){}
}
