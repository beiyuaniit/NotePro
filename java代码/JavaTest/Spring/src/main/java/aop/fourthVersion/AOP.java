package aop.fourthVersion;

/**
 * @author: beiyuan
 * @className: AOP
 * @date: 2022/6/29  17:03
 */
public interface AOP {
    //有默认实现，切面实现类根据需要去重写
    default void before(){}
    default void after(){}
    default void exception(){}
}
