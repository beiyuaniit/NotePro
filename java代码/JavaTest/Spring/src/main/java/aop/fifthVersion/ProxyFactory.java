package aop.fifthVersion;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: beiyuan
 * @className: ProxyFactory
 * @date: 2022/6/29  20:17
 */
public class ProxyFactory {
    public static Object getAgent(Service target,AOP aop){
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args)
                            throws Throwable {
                        Object obj=null;
                        try{
                            aop.before();
                            obj=method.invoke(target,args);
                            aop.after();
                        }catch (Exception e){
                            aop.exception();
                        }
                        return obj;
                    }
                }
        );

    }
}
