package demo.config;

import demo.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: beiyuan
 * @className: AdminWebConfig
 * @date: 2022/7/28  18:38
 */
@Configuration
public class AdminWebConfig implements WebMvcConfigurer {
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/car")
//                .excludePathPatterns("/css/**","/fonts/**","/images/**",
//                        "/js/**");
//    }
}
