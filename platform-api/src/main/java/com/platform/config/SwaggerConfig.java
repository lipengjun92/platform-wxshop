package com.platform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;

/**
 * @author pengjun
 * @gitee https://gitee.com/fuyang_lipengjun/platform
 */
@Configuration
@EnableWebMvc
@EnableSwagger2WebMvc
@ComponentScan(basePackages = "com.platform.api")
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.apiInfo())
                .groupName("API")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.platform.api"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        @SuppressWarnings("deprecation")
        ApiInfo info = new ApiInfo(
                "开源微同商城",
                description(),
                "1.0",
                "http://fly2you.cn/platform-framework",
                new Contact("lipengjun", "https://gitee.com/fuyang_lipengjun", "939961241@qq.com"),
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList<>());
        return info;
    }

    private String description() {
        return "减少重复造轮子，开源微信小程序商城（前后端开源：uniapp+Java），拼团、秒杀、优惠券、积分购物、直播卖货、分销等功能。快速搭建一个属于自己的微信小程序商城。QQ交流群：66502035、870579539、151602347欢迎大家进群交流技术。体验地址：http://fly2you.cn/platform-framework/login.html";
    }
}
