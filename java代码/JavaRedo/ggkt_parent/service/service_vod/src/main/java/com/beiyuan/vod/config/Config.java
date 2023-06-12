package com.beiyuan.vod.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: beiyuan
 * @date: 2022/11/1  22:48
 */
@Configuration

public class Config {


        //mubatis-plus 分页插件
        @Bean
        public PaginationInterceptor paginationInterceptor(){

            return new PaginationInterceptor();
        }

}
