package aop.firstVersion;

/**
 * @author: beiyuan
 * @className: BookServiceImpl
 * @date: 2022/6/29  16:07
 */
public class BookServiceImpl {
    public void buy(){
        try {
            System.out.println("开启事务");
            System.out.println("购买图书业务实现");
            System.out.println("事务提交");
        } catch (Exception e) {
            System.out.println("事务回滚");
        }
    }
}
