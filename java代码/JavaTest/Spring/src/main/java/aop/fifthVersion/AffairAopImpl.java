package aop.fifthVersion;

/**
 * @author: beiyuan
 * @className: AffairAopImpl
 * @date: 2022/6/29  17:45
 */
public class AffairAopImpl implements AOP {
    @Override
    public void before() {
        System.out.println("事务开启");
    }

    @Override
    public void after() {
        System.out.println("事务提交");
    }

    @Override
    public void exception() {
        System.out.println("事务回滚");
    }
}
