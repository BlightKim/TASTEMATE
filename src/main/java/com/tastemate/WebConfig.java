package com.tastemate;


import com.tastemate.interceptor.LogInterceptor;
import com.tastemate.interceptor.LoginCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
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

    registry.addInterceptor(new LoginCheckInterceptor())
            .order(2)
            .addPathPatterns("/**")
            .excludePathPatterns("/", "/board", "/member/login**", "/member/logout",
                    "/css/**", "/*.ico", "**/js/**", "/error", "/member/register", "/img/**",
                    "/board/write/**", "/;jsessionid**", "/comments/**", "**/download/**", "**/js/**", "/member/class",
                    "/member/mbti", "/member/selectFood", "/member/list", "/member/simpleregister",
                    "/member/userJoin", "/member/findId", "/member/findIdByEmail", "/store/list", "/store/main",
                    "/member/resetPassword", "/member/reset", "/member/checkId", "/tastemate", "/member/tastemate",
                    "/member/resetSuccess", "/member/mailConfirm", "/member/sweetalert2.min.css", "/bookmark/get");

  }
  private String resourcePath = "/member/**";
  private String savePath = "file:///Users/bazzi/upload";


  private String storeResourcePath = "/store/**";
  private String storeSavePath = "file:///C:/upload/";

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry){
    registry.addResourceHandler(storeResourcePath)
            .addResourceLocations(storeSavePath);


  }

}

