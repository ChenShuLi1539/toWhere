package com.gangoffive.project.demo.tool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    private CorsConfiguration buildConfig(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*"); // 允许任何的head头部
        corsConfiguration.addAllowedOrigin("http://localhost:8080"); // 允许任何域名使用
        corsConfiguration.addAllowedOrigin("http://192.168.43.48:8081"); // 允许任何域名使用
        corsConfiguration.addAllowedOrigin("http://192.168.43.119:8081"); // 允许任何域名使用
        corsConfiguration.addAllowedOrigin("https://localhost:8081"); // 允许任何域名使用
        corsConfiguration.addAllowedOrigin("https://39.108.237.209:8081"); // 允许任何域名使用
        corsConfiguration.addAllowedOrigin("https://www.lanwenjun.ltd:8081"); // 允许任何域名使用
        corsConfiguration.addAllowedOrigin("http://192.168.0.106"); // 允许任何域名使用
        corsConfiguration.addAllowedOrigin("http://192.168.0.105"); // 允许任何域名使用

        corsConfiguration.addAllowedMethod("*"); // 允许任何的请求方法
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    // 添加CorsFilter拦截器，对任意的请求使用
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }

}
