package com.sjy.prometheus.config;

import com.sjy.prometheus.prometheus.PrometheusMetricsInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author yonyong
 */
@Configuration
public class AppConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new PrometheusMetricsInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}