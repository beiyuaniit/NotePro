package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: beiyuan
 * @className: ProxyFactory
 * @date: 2022/6/6  20:14
 */
public class ProxyFactory {
    Service target;
    public ProxyFactory(Service target){
        this.target=target;
    }

    //获取动态代理对象
    public Object getAgent(){
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(
                            Object proxy,//创建好的代理对象
                            Method method, //目标对象的方法
                            Object[] args//目标方法的参数
                    ) throws Throwable {
                        System.out.println("预定场地");
                        //调用目标方法
                        Object obj=method.invoke(target,args);
                        System.out.println("结算费用");
                        return obj;//只是invoke()方法的返回值
                    }
                }
        );
    }
}
