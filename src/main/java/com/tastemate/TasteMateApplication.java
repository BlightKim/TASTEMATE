package com.tastemate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

/*@SpringBootApplication(exclude = {SecurityAutoConfiguration.class,
    org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration.class,
    org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration.class,
    org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration.class} )*/
@SpringBootApplication
@ComponentScan(excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ContextInstanceDataAutoConfiguration.class),
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ContextStackAutoConfiguration.class),
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ContextRegionProviderAutoConfiguration.class),
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = SecurityAutoConfiguration.class)})
@Import({S3Config.class, ChatConfig.class, WebSockConfig.class})
public class TasteMateApplication {
    public static void main(String[] args) {
        SpringApplication.run(TasteMateApplication.class, args);
    }

}
