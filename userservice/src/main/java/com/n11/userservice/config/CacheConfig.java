package com.n11.userservice.config;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;

@Configuration
public class CacheConfig {
    @Primary
    @Bean
    public CacheManager defaultCacheManager(RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory).build();
    }

    @Bean
    public CacheManager recommendationsCacheManager(RedisConnectionFactory redisConnectionFactory) {
        Duration expiration = Duration.ofSeconds(5);
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig().entryTtl(expiration);
        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory).cacheDefaults(config).build();
    }
}

