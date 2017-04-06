package com.iemylife.iot.weather.config;

import com.iemylife.iot.weather.domain.vo.WeatherDataNowInfoForJson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author 潘荣甫
 * @version 1.0
 * @description
 * @date 2017/4/7
 * @since Jdk 1.8
 */
@Configuration
public class redisConfig1 {
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, WeatherDataNowInfoForJson> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, WeatherDataNowInfoForJson> template = new RedisTemplate<String, WeatherDataNowInfoForJson>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new RedisObjectSerializer());
        return template;
    }

}
