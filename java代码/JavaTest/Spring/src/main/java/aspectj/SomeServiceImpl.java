package aspectj;

/**
 * @author: beiyuan
 * @className: SomeServiceImpl
 * @date: 2022/6/30  22:05
 */

import org.springframework.stereotype.Service;

@Service
public class SomeServiceImpl implements SomeService{
    @Override
    public String doSome(String name, int age) {
        System.out.println(name+" 执行了doSome方法 "+age);
        return "abc";
    }

    @Override
    public String doAfterReturn(int i) {
        System.out.println("doAfterReturnin ");
        return null;
    }

    @Override
    public String doAround(String str) {
        System.out.println("doAround");
        return str;
    }

    @Override
    public String doAfter(String name) {
        //int a=1/0;
        System.out.println("doAfter");
        return "abc";
    }

    @Override
    public String doAll() {
        System.out.println("doAll method execute");
        return null;
    }
}
