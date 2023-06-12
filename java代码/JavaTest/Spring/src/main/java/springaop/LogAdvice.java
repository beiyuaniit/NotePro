package springaop;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author: beiyuan
 * @className: LogAdvice
 * @date: 2022/6/29  22:53
 */
public class LogAdvice implements MethodBeforeAdvice {
    /**
     * 可以通过这三个参数获取一些信息
     * @param method   被切入的目标方法
     * @param objects   该目标方法的参数
     * @param o     该目标方法的对象
     * @throws Throwable
     */
    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
        //输出当前时间，被且入的方法，及其实参值
        System.out.println("\n[系统日志]"+sf.format(new Date())+"----"+method.getName()+
                "----"+ Arrays.toString(objects));
    }
}
