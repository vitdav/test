package com.sgugo.sky.config;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@Slf4j
public class RedisConfiguration {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        //创建RedisTemplate对象
        RedisTemplate template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);

        //创建序列化工具
        //json序列化工具 对象使用此序列化
        Jackson2JsonRedisSerializer jsonSerializer = new Jackson2JsonRedisSerializer(Object.class);
        //类型转换
        ObjectMapper objectMapper = new ObjectMapper();
        //设置所有属性可见性序列化
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //默认键入全包名
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jsonSerializer.setObjectMapper(objectMapper);
        //string序列化工具 string使用此序列化
        StringRedisSerializer stringSerializer = new StringRedisSerializer();

        //配置序列化工具
        //设置KEY/key的序列化工具为stringSerializer
        template.setKeySerializer(stringSerializer);
        //设置值的序列化工具为jsonSerializer
        template.setValueSerializer(jsonSerializer);
        //设置hash的小key的序列化工具为stringSerializer
        template.setHashKeySerializer(stringSerializer);
        //设置hash的值的序列化工具为jsonSerializer
        template.setHashValueSerializer(jsonSerializer);
        template.afterPropertiesSet();
        return template;
    }
}
