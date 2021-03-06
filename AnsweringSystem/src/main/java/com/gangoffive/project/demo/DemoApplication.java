package com.gangoffive.project.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory config = new MultipartConfigFactory();
        config.setMaxFileSize(DataSize.parse("80MB"));
        config.setMaxRequestSize(DataSize.parse("100MB"));
        return config.createMultipartConfig();
    }

}
