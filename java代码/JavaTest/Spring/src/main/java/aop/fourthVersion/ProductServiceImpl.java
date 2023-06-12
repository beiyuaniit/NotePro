package aop.fourthVersion;

/**
 * @author: beiyuan
 * @className: Product
 * @date: 2022/6/29  17:37
 */
public class ProductServiceImpl implements Service{
    @Override
    public void buy() {
        System.out.println("购买商品业务实现four");
    }
}
