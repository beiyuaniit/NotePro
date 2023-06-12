package aop.fifthVersion;

/**
 * @author: beiyuan
 * @className: BookServiceImpl
 * @date: 2022/6/29  16:33
 */
//实现业务功能
public class BookServiceImpl implements Service {
    @Override
    public void buy() {
        System.out.println("购买图书业务实现four");
    }

    @Override
    public void show() {
        System.out.println("showing");
    }
}
