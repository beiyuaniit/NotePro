package com.beiyuan.music.common.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: beiyuan
 * @date: 2022/12/15  12:25
 */
@Configuration
public class MybatisPlusConfig {

    //mubatis-plus 分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor(){

        return new PaginationInterceptor();
    }
}
