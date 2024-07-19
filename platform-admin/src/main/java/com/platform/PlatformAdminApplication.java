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
package com.platform;

import cn.hutool.core.util.URLUtil;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceAutoConfiguration;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 启动类
 *
 * @author 李鹏军
 */
@Slf4j
@RequiredArgsConstructor
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, DruidDataSourceAutoConfigure.class})
@EnableAsync
@Import({DynamicDataSourceAutoConfiguration.class})
public class PlatformAdminApplication extends SpringBootServletInitializer implements ApplicationRunner {
    private final ServerProperties serverProperties;

    public static void main(String[] args) {
        SpringApplication.run(PlatformAdminApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PlatformAdminApplication.class);
    }

    /**
     * 访问首页提示
     *
     * @return /
     */
    @Hidden
    @GetMapping("/")
    public String index() throws UnknownHostException {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        Integer port = serverProperties.getPort();
        String contextPath = serverProperties.getServlet().getContextPath();
        String baseUrl = URLUtil.normalize(String.format("%s:%s%s", hostAddress, port, contextPath));
        return String.format("Backend Service Started Successfully.<br/>API: %s/doc.html", baseUrl);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        Integer port = serverProperties.getPort();
        String contextPath = serverProperties.getServlet().getContextPath();
        String baseUrl = URLUtil.normalize(String.format("%s:%s%s", hostAddress, port, contextPath));
        log.info("┌------------------------------------------------------------┐");
        log.info("|                Service Started Successfully                |");
        log.info("| API 地址：{}              |", baseUrl);
        log.info("| API 文档：{}/doc.html     |", baseUrl);
        log.info("└------------------------------------------------------------┘");
    }

}
