package com.tastemate;

import com.tastemate.interceptor.LogInterceptor;
import com.tastemate.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import lombok.RequiredArgsConstructor;
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
        .excludePathPatterns("/css/**", "/*.ico", "/error", "/img/**", "/images/**", "/js/**", "/ws/**", "**/stomp/chat/**");

    registry.addInterceptor(new LoginCheckInterceptor())
            .order(2)
            .addPathPatterns("/**")
            .excludePathPatterns("/", "/board", "/member/login**",
                    "/css/**", "/*.ico", "/js/**", "/error", "/register", "/img/**", "/;jsessionid**", "/comments/**", "**/download/**", "/member/simpleregister"
            ,"/store/**", "/booking/**", "/pay/**", "/store/get", "/bookmark/insertAjax.do", "/stomp/chat/**",
                "/chat/joinRoom", "/chat/deleteRoom/**", "/board/list"
                );

  }


  private String resourcePath = "/member/**";
  private String savePath = "file:///Users/bazzi/upload";


  private String storeResourcePath = "/store/**";
  private String storeSavePath = "file:///C:/upload/";

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry){
    registry.addResourceHandler(storeResourcePath)
            .addResourceLocations(storeSavePath);
    registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static");
  }
}
