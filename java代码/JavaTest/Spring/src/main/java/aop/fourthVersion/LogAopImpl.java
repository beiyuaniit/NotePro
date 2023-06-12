package aop.fourthVersion;

/**
 * @author: beiyuan
 * @className: LogAopImpl
 * @date: 2022/6/29  17:36
 */
public class LogAopImpl implements AOP{
    @Override
    public void before() {
        System.out.println("Logging...");
    }
}
