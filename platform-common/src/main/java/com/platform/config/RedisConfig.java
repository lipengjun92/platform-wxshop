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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.platform.common.utils.Constant;
import com.platform.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

/**
 * Redis配置
 *
 * @author 李鹏军
 */
@Slf4j
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private Integer port;

    @Value("${spring.redis.timeout}")
    private String strTimeout;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.database}")
    private Integer database;

    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxActive;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private String strMaxWaitMillis;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private Integer maxIdle;

    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle;

    /**
     * 采用RedisCacheManager做为缓存管理器
     */
    @Bean
    @Primary
    @Override
    public CacheManager cacheManager() {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(6))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keyRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueRedisSerializer()));
        return RedisCacheManager.builder(redisConnectionFactory()).cacheDefaults(config).build();
    }

    @Bean
    @Primary
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            String re = String.format("%s::%s%s", target.getClass().getName(), method.getName(),
                    CollectionUtils.arrayToList(params));
            log.debug("缓存生成的key:{}", re);
            return re;
        };
    }

    @Bean
    public Jackson2JsonRedisSerializer<Object> valueRedisSerializer() {
        ObjectMapper objectMapper = new ObjectMapper();
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常,序列化时将对象全类名一起保存下来
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }

    @Bean
    public StringRedisSerializer keyRedisSerializer() {
        return new StringRedisSerializer();
    }

    /**
     * RedisTemplate
     *
     * @return redisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        // key采用String的序列化方式
        template.setKeySerializer(keyRedisSerializer());
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(keyRedisSerializer());
        // value序列化方式采用jackson
        template.setValueSerializer(valueRedisSerializer());
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(valueRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        long maxWaitMillis = Long.parseLong(strMaxWaitMillis.replace("ms", Constant.BLANK));
        int timeout = Integer.parseInt(strTimeout.replace("ms", Constant.BLANK));
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxActive);
        jedisPoolConfig.setMaxWait(Duration.ofMillis(maxWaitMillis));
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setTestOnBorrow(true);
        if (StringUtils.isBlank(password)) {
            password = null;
        }

        JedisClientConfiguration clientConfig = JedisClientConfiguration.builder().usePooling().poolConfig(jedisPoolConfig).and().readTimeout(Duration.ofMillis(timeout)).build();
        // 单点redis
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        // 哨兵redis
        // RedisSentinelConfiguration redisConfig = new RedisSentinelConfiguration();
        // 集群redis
        // RedisClusterConfiguration redisConfig = new RedisClusterConfiguration();
        redisConfig.setHostName(host);
        redisConfig.setPort(port);
        redisConfig.setPassword(password);
        redisConfig.setDatabase(database);

        return new JedisConnectionFactory(redisConfig, clientConfig);
    }
}
