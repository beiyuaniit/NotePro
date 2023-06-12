package demo.config;

import demo.servlet.MyServlet;
import demo.servlet.RegServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @author: beiyuan
 * @className: MyRegistConfig
 * @date: 2022/7/31  20:29
 */
@Configuration(proxyBeanMethods = true)
public class MyRegistConfig {
    @Bean
    public ServletRegistrationBean servltReg(){
        RegServlet regServlet=new RegServlet();
        return new ServletRegistrationBean(regServlet,"/regServlet");
    }
}
