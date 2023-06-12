package aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;

/**
 * @author: beiyuan
 * @className: aspectj.SomeService
 * @date: 2022/6/30  22:04
 */
public interface SomeService {
    String doSome(String name,int age);
    String doAfterReturn(int i);
    String doAround(String str);
    String doAfter(String name);

    String doAll();
}
