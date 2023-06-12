package org.八;

/**
 * @author: beiyuan
 * @className: MyFunctionalInterface
 * @date: 2022/8/11  10:03
 */
@FunctionalInterface
public interface MyFunctionalInterface {
    String getname();
    default void sayHi(){};
}
