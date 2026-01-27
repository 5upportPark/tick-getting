package com.pjw.tickgettinig.service;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

  private final RedisTemplate<String, Object> redisTemplate;

  public Long getLong(String key) {
    Object value = redisTemplate.opsForValue().get(key);
    if (Objects.isNull(value)) {
      return 0L;
    }
    return Long.valueOf(value.toString());
  }

  public Long decreaseLong(String key) {
    return redisTemplate.opsForValue().increment(key);
  }

  public Long decreaseLong(String key, Long value) {
    return redisTemplate.opsForValue().increment(key, value);
  }
}
