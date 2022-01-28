package com.github.white.at.framework.config;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.github.white.at.framework.basic.constants.Consts;

@EnableRedisHttpSession
@Configuration
public class RedisConfig {

    private final ObjectMapper objectMapper;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${spring.profiles.active}")
    private String profile;

    public RedisConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        StringRedisSerializer keySerializer = new PrefixStringRedisSerializer(applicationName, profile);
        Jackson2JsonRedisSerializer<Object> valueSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        objectMapper.copy()
            .activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);

        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(keySerializer);
        template.setValueSerializer(valueSerializer);
        // Hash的key也采用StringRedisSerializer的序列化方式
        template.setHashKeySerializer(keySerializer);
        template.setHashValueSerializer(valueSerializer);
        template.afterPropertiesSet();
        return template;
    }

    public static class PrefixStringRedisSerializer extends StringRedisSerializer {

        private final String name;

        private final String profile;

        public PrefixStringRedisSerializer(String name, String profile) {
            this.name = name;
            this.profile = profile;
        }

        @Override
        public String deserialize(byte[] bytes) {
            return Optional.ofNullable(super.deserialize(bytes)).map(i -> i.split(Consts.COLON)[2]).orElse(null);
        }

        @Override
        public byte[] serialize(String string) {
            String k = Optional.ofNullable(string).map(i -> StringUtils.joinWith(Consts.COLON, name, profile, i))
                .orElse(null);
            return super.serialize(k);
        }
    }
}
