package com.rainhard.modulith.system.config;


import com.rainhard.modulith.system.RequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class RequestFilterConfig {

    @Bean
    public FilterRegistrationBean<RequestFilter> filterRegistrationBean(){
        FilterRegistrationBean<RequestFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new RequestFilter());
        bean.addUrlPatterns("/*");
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
