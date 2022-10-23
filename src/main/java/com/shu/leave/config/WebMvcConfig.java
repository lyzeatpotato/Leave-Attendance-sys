package com.shu.leave.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    //跨域配置
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")      // 允许哪些路径能够跨域访问
                .allowedHeaders("*")        // 允许头部设置
                .allowedMethods("*")        // 允许请求的方法（如post, get ,put, options, delete）
                .allowedOrigins("*")        // 允许跨域访问的源
                .maxAge(168000);             // 预检间隔时间
//                .allowCredentials(true);    // 是否允许发送cookie
    }
}
