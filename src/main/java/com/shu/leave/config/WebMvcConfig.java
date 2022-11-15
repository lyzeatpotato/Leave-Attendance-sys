package com.shu.leave.config;

import com.shu.leave.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private AuthInterceptor authInterceptor;        // 引入jwt验证拦截

    // 全局跨域配置
    //@Override
    //public void addCorsMappings(CorsRegistry registry) {
    //    registry.addMapping("/**")      // 允许哪些路径能够跨域访问
    //            .allowedHeaders("*")        // 允许头部设置
    //            .allowedMethods("*")        // 允许请求的方法（如post, get ,put, options, delete）
    //            .allowedOrigins("*")        // 允许跨域访问的源
    //            .maxAge(168000);             // 预检间隔时间
    //}

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/*/**");
    }
}
