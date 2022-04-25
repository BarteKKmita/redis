package com.example.redistest.configuration;

import com.example.redistest.student.StudentEntity;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.convert.RedisCustomConversions;

@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfiguration {

  @Bean
  public RedisTemplate<String, StudentEntity> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, StudentEntity> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    return template;
  }

  @Bean
  public RedisCustomConversions redisCustomConversions(){
    return new RedisCustomConversions(List.of(new StringKeyWriter(),new StringKeyReader(),
        new ByteKeyWriter(), new ByteKeyReader()));
  }
  @WritingConverter
  public static class StringKeyWriter implements Converter<StudentKey,String>{
    @Override
    public String convert(StudentKey source) {
      return source.getId()+"/"+source.getAge();
    }
  }
  @WritingConverter
  public static class ByteKeyWriter implements Converter<StudentKey,byte []>{
    @Override
    public byte[] convert(StudentKey source) {
      return (source.getId()+"/"+source.getAge()).getBytes(StandardCharsets.UTF_8);
    }
  }
  @ReadingConverter
  public static class StringKeyReader implements Converter<String,StudentKey>{

    @Override
    public StudentKey convert(String source) {
      String[] split = source.split("/");
      return new StudentKey(split[0], split[1]);
    }
  }

  @ReadingConverter
  public static class ByteKeyReader implements Converter<byte[],StudentKey>{

    @Override
    public StudentKey convert(byte[] source) {
      String key = new String(source, StandardCharsets.UTF_8);
      String[] split = key.split("/");
      return new StudentKey(split[0], split[1]);
    }
  }
}
