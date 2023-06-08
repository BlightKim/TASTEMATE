package com.tastemate;


import com.tastemate.interceptor.LogInterceptor;
import com.tastemate.interceptor.LoginCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebSecurity
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LogInterceptor())
        .order(1)
        .addPathPatterns("/**")
        .excludePathPatterns("/css/**", "/*.ico", "/error", "/img/**", "/images/**", "/js/**");
//
//    registry.addInterceptor(new LoginCheckInterceptor())
//        .order(2)
//        .addPathPatterns("/**")
//        .excludePathPatterns("/", "/board", "/login/**", "/login/logout",
//            "/css/**", "/*.ico", "**/js/**", "/error", "/register", "/img/**",
//            "/board/write/**", "/;jsessionid**", "/comments/**", "**/download/**", "**/js/**");

  }
  private String resourcePath = "/member/**";
  private String savePath = "file:///Users/bazzi/upload";

/*  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry){
    registry.addResourceHandler(resourcePath)
        .addResourceLocations(savePath);
  }*/
}
