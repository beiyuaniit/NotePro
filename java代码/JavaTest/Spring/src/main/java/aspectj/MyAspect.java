package aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author: beiyuan
 * @className: MyAspect
 * @date: 2022/6/30  22:07
 */
@Aspect         //交给AspectJ框架去识别切面类
@Component
public class MyAspect {
    /*
    切面的功能都是由切面方法来完成
    前置通知规范
        访问权限是public
        返回值是void
        方法名自定义
        没有参数，有也只能是JoinPoint类型
        用@Before注解声明前置切入及其切入点
            属性:value 指定切入点表达式
     */
    //@Before(value="execution(* aspectj.SomeService.*(..))")
    public void myBefore1(JoinPoint jp){
        System.out.println("方法签名："+jp.getSignature());
        System.out.println("实参 "+ Arrays.toString(jp.getArgs()));
    }

    @Before(value="execution(public String aspectj.SomeService.doSome(String,int))")//较完整的
    public void myBefore(){
        System.out.println("doSome 方法的前置通知");
    }

    /*
    后置通知规范
        权限public
        返回值void
        名称自定义
        可有参数(常用 Object obj为目标方法执行后的返回值,void则为null)，目前好像只能有这一个参数
            当然也可以没有，无论目标方法本身有没有参数
        @AfterReturning注解
            value:切入点表达式
            returning:指定目标方法返回值名称，得与切面方法的参数一致

     */
    @AfterReturning(value = "execution(* aspectj.SomeServiceImpl.doAfterReturn(..))",returning="obj")
    public void myAfterReturning(Object obj){
        System.out.println("后置通知");
        if(obj!=null){
            //分情况来决定是否可以改变目标方法的返回值
            if(obj instanceof String){
                //8种基本类型和String都不可以改变
                obj=obj.toString().toLowerCase();//无影响
            }
            if(obj instanceof SomeService){
                //引用类型，可以改变
            }
        }
    }

    /*
    环绕通知规范
        权限是public
        返回值作为目标方法的返回值
        方法名自定义
        有参数，此参数就是目标方法
        回避（抛出）Throwable异常。why？
        @Around
            value:切入点表达式
     */
    @Around(value = "execution(* aspectj.SomeServiceImpl.doAround(String))")
    public String aroundAspect(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("前Around");

        //传递参数，调用目标方法，初步拿到目标方法返回值
        Object obj=pjp.proceed(pjp.getArgs());//此处要抛出Throwable。

        System.out.println("后Around");
        return obj.toString().toUpperCase();//这里才作为目标方法最终的返回值
    }

    /*
    最终通知规范
        权限public
        返回值void
        方法名自定义
        方法可没有参数，有也只能是JoinPoint类型
        @After
            value:切入点表达式
     */
    @After(value = "execution(* aspectj.SomeServiceImpl.doAfter(..))")
    public void myAfter(JoinPoint jp){
        System.out.println("签名 "+jp.getSignature());
        System.out.println("After");
    }

    //测试不同通知的执行顺序
    //切入点表达式取别名
    @Pointcut(value = "execution(* aspectj.SomeServiceImpl.doAll(..))")
    public void myCut(){}

    //取的时候直接调用函数
    @Before(value = "myCut()")
    public void myBefore1(){
        System.out.println("@Before");
    }

    @AfterReturning(value = "myCut()")
    public void myAfterReturning2(){
        System.out.println("@AfterReturning");
    }

    @After(value = "myCut()")
    public void myAfter2(){
        System.out.println("@After");
    }

    @Around(value = "myCut()")
    public void myAround(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("@Around-pre");
        pjp.proceed(pjp.getArgs());
        System.out.println("@Around-next");
    }

}
