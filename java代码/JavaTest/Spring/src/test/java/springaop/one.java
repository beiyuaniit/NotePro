package springaop;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: beiyuan
 * @className: one
 * @date: 2022/6/29  22:48
 */
public class one {
    @Test
    public void ont(){
        ApplicationContext ac=new ClassPathXmlApplicationContext("springaop.xml");
        BookService bookService=(BookService) ac.getBean("bookService");
        bookService.bug("libai","qiangjinjiu",21);
        bookService.comment("liqingzhao","qiqicancanqiqi");
    }
}
