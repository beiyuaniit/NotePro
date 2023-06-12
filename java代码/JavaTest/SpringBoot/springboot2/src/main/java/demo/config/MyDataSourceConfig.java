package demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * @author: beiyuan
 * @className: MyDataSourceConfig
 * @date: 2022/8/2  14:30
 */
//@Configuration
public class MyDataSourceConfig {

//    @Bean
//    @ConfigurationProperties("spring.datasource")
//    public DataSource dataSource() throws SQLException {
//        DruidDataSource druidDataSource=new DruidDataSource();
//        druidDataSource.setFilters("stat,wall");
//        return druidDataSource;
//    }
//
//    @Bean
//    public ServletRegistrationBean statViewServlet(){
//        StatViewServlet statViewServlet=new StatViewServlet();
//        ServletRegistrationBean<StatViewServlet> registrationBean=new
//                ServletRegistrationBean<>(statViewServlet,"/druid/*");
//        registrationBean.addInitParameter("loginUsername","admin");
//        registrationBean.addInitParameter("loginPassword","123");
//        return registrationBean;
//    }
//
//    @Bean
//    public FilterRegistrationBean webStatFilter(){
//        WebStatFilter webStatFilter=new WebStatFilter();
//        FilterRegistrationBean<WebStatFilter> registrationBean=new FilterRegistrationBean<>(webStatFilter);
//        registrationBean.setUrlPatterns(Arrays.asList("/*"));
//        registrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
//        return registrationBean;
//    }
}
