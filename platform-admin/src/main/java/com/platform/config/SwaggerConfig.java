/*
 *
 *      Copyright (c) 2018-2099, lipengjun All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the fly2you.cn developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lipengjun (939961241@qq.com)
 *
 */
package com.platform.config;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Swagger配置
 * Parameter Types
 * OpenAPI 3.0 distinguishes between the following parameter types based on the parameter location. The location is determined by the parameter’s in key, for example, in: query or in: path.
 * <p>
 * path parameters, such as /users/{id}
 * query parameters, such as /users?role=admin
 * header parameters, such as X-MyHeader: Value
 * cookie parameters, which are passed in the Cookie header, such as Cookie: debug=0; csrftoken=BUSe35dohU3O1MZvDCU
 * <p>
 * Data Type
 * "double",
 * "float",
 * "long",
 * "integer",
 * "byte",
 * "boolean",
 * "string",
 * "object",
 * <p>
 * 注意：
 * integer、double、float、long、byte、需要给example的值
 *
 * @author 李鹏军
 */
@Configuration
@EnableWebMvc
public class SwaggerConfig {

    @Value("${projectVersion}")
    private String projectVersion;

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("微同软件")
                .description("微同软件管理端接口文档")
                .termsOfService("https://fly2you.cn")
                .contact(new Contact().name("李鹏军").email("939961241@qq.com").url("https://gitee.com/fuyang_lipengjun"))
                .version("Application Version: " + projectVersion + ", Spring Boot Version: " + SpringBootVersion.getVersion());
    }

    /**
     * 根据 @Tag 上的排序，写入 x-order
     *
     * @return the global open api customizer
     */
    @Bean
    public GlobalOpenApiCustomizer orderGlobalOpenApiCustomizer() {
        return openApi -> {
            if (null != openApi.getTags()) {
                openApi.getTags()
                        .forEach(tag -> tag.setExtensions(MapUtil.of("x-order", RandomUtil.randomInt(0, 100))));
            }
        };
    }
}
