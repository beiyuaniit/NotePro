package aop.fourthVersion;

/**
 * @author: beiyuan
 * @className: Agent
 * @date: 2022/6/29  16:37
 */
//代理目标类，增加切面
public class Agent implements Service {
    Service target;
    AOP aop;
    //根据不同的参数可以代理不同的目标对象，灵活切换
    public Agent(Service target,AOP aop) {
        this.target = target;
        this.aop=aop;
    }
    //增加切面
    @Override
    public void buy() {
        try{
            aop.before();
            target.buy();
            aop.after();
        }catch (Exception e){
            aop.exception();
        }
    }
}
