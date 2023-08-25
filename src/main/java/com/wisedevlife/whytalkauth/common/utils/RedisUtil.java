package com.wisedevlife.whytalkauth.common.utils;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, String> redisTemplate;

    public void setWithExpired(String key, String value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().setIfAbsent(key, value, timeout, timeUnit);
    }

    public void setMapWithExpired(
            String key, Map<Object, Object> value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForHash().putAll(key, value);
        redisTemplate.opsForValue().getAndExpire(key, timeout, timeUnit);
    }
}
