package com.files.filesman;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class MyWebMVCConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/file/**")
                .addResourceLocations("file:/Users/zhaoxiangyuan/Documents/")
                .addResourceLocations("file:/Users/zhaoxiangyuan/Documents/img/")
                .addResourceLocations("file:/Users/zhaoxiangyuan/Documents/22/")
                .addResourceLocations("file:/Users/zhaoxiangyuan/Documents/33/")
        ;

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .maxAge(1800)
                .allowedOrigins("*");
    }
}
