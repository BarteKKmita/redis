package com.example.redistest.configuration;

import com.example.redistest.student.StudentEntity;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfiguration {

  @Bean
  public RedisTemplate<String, StudentEntity> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, StudentEntity> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    return template;
  }
}
