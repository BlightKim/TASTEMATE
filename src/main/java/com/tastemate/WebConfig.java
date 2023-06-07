package com.tastemate;

import com.tastemate.interceptor.LogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LogInterceptor())
        .order(1)
        .addPathPatterns("/**")
        .excludePathPatterns("/css/**", "/*.ico", "/error");
  }

  private String resourcePath = "/member/**";
  private String savePath = "file:///Users/bazzi/upload";

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry){
    registry.addResourceHandler(resourcePath)
            .addResourceLocations(savePath);
  }
}
