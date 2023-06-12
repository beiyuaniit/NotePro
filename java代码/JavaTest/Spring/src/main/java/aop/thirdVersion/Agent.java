package aop.thirdVersion;

/**
 * @author: beiyuan
 * @className: Agent
 * @date: 2022/6/29  16:37
 */
//代理目标类，增加切面
public class Agent implements Service {
    Service target;
    //根据不同的参数可以代理不同的目标对象，灵活切换
    public Agent(Service target) {
        this.target = target;
    }
    //增加切面
    @Override
    public void buy() {
        try{
            System.out.println("事务开启");
            target.buy();
            System.out.println("事务提交");
        }catch (Exception e){
            System.out.println("事务回滚");
        }
    }
}
