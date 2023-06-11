//package com.tastemate;
//
//import com.tastemate.interceptor.LogInterceptor;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class WebConfig implements WebMvcConfigurer {
//
//  @Override
//  public void addInterceptors(InterceptorRegistry registry) {
//    registry.addInterceptor(new LogInterceptor())
//        .order(1)
//        .addPathPatterns("/**")
//        .excludePathPatterns("/css/**", "/*.ico", "/error");
//  }
//
//  private String resourcePath = "/member/**";
//  private String savePath = "file:///Users/bazzi/upload";
//
//  @Override
//  public void addResourceHandlers(ResourceHandlerRegistry registry){
//    registry.addResourceHandler(resourcePath)
//            .addResourceLocations(savePath);
//  }
//
//}
//
//
//
//

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

    registry.addInterceptor(new LoginCheckInterceptor())
            .order(2)
            .addPathPatterns("/**")
            .excludePathPatterns("/", "/board", "/member/login**", "/member/logout",
                    "/css/**", "/*.ico", "**/js/**", "/error", "/member/register", "/img/**",
                    "/board/write/**", "/;jsessionid**", "/comments/**", "**/download/**", "**/js/**", "/member/class",
                    "/member/mbti", "/member/selectFood", "/store/**", "member/list", "/member/simpleregister", "/member/userJoin");

  }
  private String resourcePath = "/member/**";
  private String savePath = "file:///Users/bazzi/upload";

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry){
    registry.addResourceHandler(resourcePath)
            .addResourceLocations(savePath);
  }

}

