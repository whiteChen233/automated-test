package com.github.white.at.framework.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.github.white.at.utils.DateFormatter;

/**
 * JacksonConfig: The type Jackson config.
 *
 * @author White
 * @version 1.0
 * @date 2021/08/10 下午 10:12
 */
@Configuration
public class JacksonConfig {

    /**
     * Jackson 2 object mapper builder customizer jackson 2 object mapper builder customizer.
     *
     * @return the jackson 2 object mapper builder customizer
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            // 针对于JDK新时间类。序列化时带有T的问题，自定义格式化字符串
            JavaTimeModule javaTimeModule = new JavaTimeModule();
            DateTimeFormatter pattern = DateFormatter.Y4_M2_D2_H2_M2_S2.get();
            javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(pattern));
            javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(pattern));

            builder
                // 若POJO对象的属性值为null，序列化时显示
                .serializationInclusion(JsonInclude.Include.ALWAYS)
                // 若POJO对象的属性值为""，序列化时显示
                .serializationInclusion(JsonInclude.Include.ALWAYS)
                // DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES 相当于配置，JSON串含有未知字段时，反序列化依旧可以成功
                .failOnUnknownProperties(true)
                // 序列化时的命名策略——驼峰命名法
                .propertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE)
                // 针对于Date类型，文本格式化
                .simpleDateFormat(DateFormatter.Y4_M2_D2_H2_M2_S2.getPattern())
                // 注册时间模块
                .modules(javaTimeModule)
                // 默认关闭，将char[]数组序列化为String类型。若开启后序列化为JSON数组。
                .featuresToEnable(SerializationFeature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS)
                // 默认关闭，在类上使用@JsonRootName(value="rootNode")注解时是否可以包裹Root元素
                // .featuresToEnable(SerializationFeature.WRAP_ROOT_VALUE)
                // 默认关闭，即以文本(ISO-8601)作为Key，开启后，以时间戳作为Key
                .featuresToEnable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS)
                // 默认禁用，禁用情况下，需考虑WRITE_ENUMS_USING_TO_STRING配置。启用后，ENUM序列化为数字
                .featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_INDEX)
                //默认关闭，即使用BigDecimal.toString()序列化。开启后，使用 BigDecimal.toPlainString 序列化，不输出科学计数法的值
                .featuresToEnable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN)
                // 默认关闭，当JSON字段为""(EMPTY_STRING)时，解析为普通的POJO对象抛出异常。开启后，该POJO的属性值为null
                .featuresToEnable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
                // 默认关闭，若POJO中不含有JSON中的属性，则抛出异常。开启后，不解析该字段，而不会抛出异常
                .featuresToEnable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                // 默认开启：如果一个类没有public的方法或属性时，会导致序列化失败。关闭后，会得到一个空JSON串
                .featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                // 默认开启，将Date类型序列化为数字时间戳(毫秒表示).关闭后，序列化为文本表现形式(2019-10-23T01:58:58.308+0000) 若设置时间格式化,那么均输出格式化的时间类型
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                // 指定要序列化的域，field get 和 set 以及修饰符范围，any 包括 public 和 private
                .visibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        };
    }
}
