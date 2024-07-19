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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 时间处理
 *
 * @author 李鹏军
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter<?> messageConverter : converters) {
            if (messageConverter instanceof MappingJackson2HttpMessageConverter) {
                //设置日期格式
                ObjectMapper objectMapper = new ObjectMapper();
                //反序列化忽略实体中不存在的参数
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                //jackson时间格式化
                objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                ((MappingJackson2HttpMessageConverter) messageConverter).setObjectMapper(objectMapper);

                //设置中文编码格式
                List<MediaType> list = new ArrayList<>();
                list.add(new MediaType("application", "json", StandardCharsets.UTF_8));
                ((MappingJackson2HttpMessageConverter) messageConverter).setSupportedMediaTypes(list);
            }
        }
    }
}
