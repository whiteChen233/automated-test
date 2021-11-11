package com.github.white.at.framework.utils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * JSONUtils: JSON工具
 *
 * @author White
 * @version 1.0
 * @date 2021/8/4 9:55
 */
@Slf4j
public enum JSON {

    /**
     * 工具实例
     */
    INSTANCE;

    /**
     * objectMapper
     */
    private final ObjectMapper mapper;

    /**
     * Instantiates a new Json.
     */
    JSON() {
        mapper = SpringUtil.getBean(ObjectMapper.class);
    }

    /**
     * Parse json node.
     *
     * @param text the text
     * @return the json node
     */
    public JsonNode parse(String text) {
        try {
            return mapper.readTree(text);
        } catch (Exception e) {
            // ...
        }
        return NullNode.getInstance();
    }

    /**
     * Create object node.
     *
     * @return the object node
     */
    public ObjectNode create() {
        return mapper.createObjectNode();
    }

    /**
     * To string string.
     *
     * @param obj the obj
     * @return the string
     */
    public String toString(Object obj) {
        return Optional.ofNullable(obj).map(i -> toString(i, () -> "", false)).orElse("");
    }

    /**
     * To format string string.
     *
     * @param obj the obj
     * @return the string
     */
    public String toFormatString(Object obj) {
        return Optional.ofNullable(obj).map(i -> toString(i, () -> "", true)).orElse("");
    }

    /**
     * To string string.
     *
     * @param obj             the obj
     * @param defaultSupplier the default supplier
     * @param format          the format
     * @return the string
     */
    public String toString(Object obj, Supplier<String> defaultSupplier, boolean format) {
        try {
            if (obj == null) {
                return defaultSupplier.get();
            }
            if (obj instanceof String) {
                return obj.toString();
            }
            if (obj instanceof Number) {
                return obj.toString();
            }
            if (format) {
                return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            }
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.error("toJSONString: {}", Objects.toString(obj, "null"), e);
        }
        return defaultSupplier.get();
    }

    /**
     * Deep clone t.
     *
     * @param <T>   the type parameter
     * @param obj   the obj
     * @param clazz the clazz
     * @return the t
     */
    public <T> T deepClone(Object obj, Class<T> clazz) {
        return Optional.ofNullable(obj).map(i -> toObject(toString(i), clazz)).orElse(null);
    }

    /**
     * To object t.
     *
     * @param <T>             the type parameter
     * @param value           the value
     * @param clazz           the clazz
     * @param defaultSupplier the default supplier
     * @return the t
     */
    public <T> T toObject(String value, Class<T> clazz, Supplier<T> defaultSupplier) {
        return process(value, defaultSupplier, clazz);
    }

    /**
     * To object t.
     *
     * @param <T>             the type parameter
     * @param value           the value
     * @param ref             the ref
     * @param defaultSupplier the default supplier
     * @return the t
     */
    public <T> T toObject(String value, TypeReference<T> ref, Supplier<T> defaultSupplier) {
        return process(value, defaultSupplier, ref);
    }

    /**
     * To object t.
     *
     * @param <T>             the type parameter
     * @param value           the value
     * @param javaType        the java type
     * @param defaultSupplier the default supplier
     * @return the t
     */
    public <T> T toObject(String value, JavaType javaType, Supplier<T> defaultSupplier) {
        return process(value, defaultSupplier, javaType);
    }

    /**
     * To object t.
     *
     * @param <T>   the type parameter
     * @param value the value
     * @param clazz the clazz
     * @return the t
     */
    public <T> T toObject(String value, Class<T> clazz) {
        return toObject(value, clazz, () -> null);
    }

    /**
     * To object t.
     *
     * @param <T>   the type parameter
     * @param value the value
     * @param ref   the ref
     * @return the t
     */
    public <T> T toObject(String value, TypeReference<T> ref) {
        return toObject(value, ref, () -> null);
    }

    /**
     * To object t.
     *
     * @param <T>   the type parameter
     * @param value the value
     * @param type  the type
     * @return the t
     */
    public <T> T toObject(String value, JavaType type) {
        return toObject(value, type, () -> null);
    }

    /**
     * To list list.
     *
     * @param <T>   the type parameter
     * @param value the value
     * @param clazz the clazz
     * @return the list
     */
    public <T> List<T> toList(String value, Class<T> clazz) {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, clazz);
        return toObject(value, javaType);
    }

    /**
     * To map map.
     *
     * @param value the value
     * @return the map
     */
    public Map<String, Object> toMap(String value) {
        return toObject(value, new TypeReference<LinkedHashMap<String, Object>>() {});
    }

    /**
     * Process t.
     *
     * @param <T>             the type parameter
     * @param value           the value
     * @param defaultSupplier the default supplier
     * @param arg             the arg
     * @return the t
     */
    @SuppressWarnings("unchecked")
    private <T> T process(String value, Supplier<T> defaultSupplier, Object arg) {
        try {
            if (StringUtils.isBlank(value)) {
                return defaultSupplier.get();
            }
            if (arg instanceof Class) {
                return mapper.readValue(value, (Class<T>) arg);
            }
            if (arg instanceof TypeReference) {
                return mapper.readValue(value, (TypeReference<T>) arg);
            }
            if (arg instanceof JavaType) {
                return mapper.readValue(value, (JavaType) arg);
            }
            log.error("Unsupported type: {}", arg);
        } catch (Exception e) {
            log.error("toJavaObject exception: {} - {}", value, arg, e);
        }
        return defaultSupplier.get();
    }
}

