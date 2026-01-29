package com.pjw.tickgettinig.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

  private final RedisTemplate<String, String> redisTemplate;

  public Long getLong(String key) {
    Object value = redisTemplate.opsForValue().get(key);
    if (Objects.isNull(value)) {
      return 0L;
    }
    return Long.valueOf(value.toString());
  }

  public void addSet(String key, String... value) {
    redisTemplate.opsForSet().add(key, value);
  }

  // 대기 번호 조회
  public Long getZSetRank(String key, Long userId) {
    return redisTemplate.opsForZSet().rank(key, String.valueOf(userId));
  }

  // 대기열 수 조회
  public Long getZSetTotalCount(String key) {
    return redisTemplate.opsForZSet().count(key, 0, Double.MAX_VALUE);
  }

  // 대기열 추가
  public void addZSetTimeScore(String key, Long userId) {
    redisTemplate.opsForZSet().add(key, String.valueOf(userId), System.currentTimeMillis());
  }

  public Set<String> getZSetRange(String key, int count) {
    return redisTemplate.opsForZSet().range(key, 0, count - 1);
  }

  public void removeZSet(String key, String... userId) {
    redisTemplate.opsForZSet().remove(key, userId);
  }

  public void removeSet(String key, String... userId) {
    redisTemplate.opsForSet().remove(key, userId);
  }

  public Long decreaseLong(String key) {
    return redisTemplate.opsForValue().increment(key, -1L);
  }

  public Long decreaseLong(String key, Long value) {
    return redisTemplate.opsForValue().increment(key, value);
  }

  public boolean hasValueZSet(String key, Object... val) {
    if (val.length == 1) {
      return redisTemplate.opsForZSet().score(key, val[0]) != null;
    }

    List<Double> list = redisTemplate.opsForZSet().score(key, val);

    return list != null && !list.isEmpty();
  }

  public boolean hasValueSet(String key, Object... val) {
    if (val.length == 1) {
      return redisTemplate.opsForSet().isMember(key, val[0]) != null;
    }

    Map<Object, Boolean> list = redisTemplate.opsForSet().isMember(key, val);

    return list != null && !list.isEmpty();
  }
}
