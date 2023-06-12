package aop.secondVersion;

/**
 * @author: beiyuan
 * @className: SubBookServiceImpl
 * @date: 2022/6/29  16:19
 */
public class SubBookServiceImpl extends BookServiceImpl{
    @Override
    public void buy() {
        try {
            System.out.println("事务开启");
            super.buy();
            System.out.println("事务提交");
        } catch (Exception e) {
            System.out.println("事务回滚");
        }
    }
}
