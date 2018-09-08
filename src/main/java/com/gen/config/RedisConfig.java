package com.gen.config;

import com.cloud.common.redis.PojoSerializable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author liulijun
 * redis配置类
 *  此类的主要配置功能为:序列化、cacheManager、redisTemplate、键值生成策略
 */
@Configuration
@ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig extends CachingConfigurerSupport {
    /**
     * 不使用事务
     * @param connectionFactory
     * @return
     */
    @Bean(name="redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate template = new RedisTemplate();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer()) ;
        template.setEnableTransactionSupport(false);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 使用事务
     * @param connectionFactory
     * @return
     */
    @Bean(name="transRedisTemplate")
    public RedisTemplate transRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate template = new RedisTemplate();
        template.setConnectionFactory(connectionFactory);
        //使用Pojo进行序列化操作,或者使用FastJsonRedisSerializer也行
        PojoSerializable pojoSerializable = new PojoSerializable(Object.class);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer()) ;
        template.setEnableTransactionSupport(true);
        template.afterPropertiesSet();
        return template;
    }

}
