package com.pjw.tickgettinig.service;

import com.pjw.tickgettinig.common.ErrorCode;
import com.pjw.tickgettinig.common.exceptions.BusinessException;
import com.pjw.tickgettinig.config.RateLimiterConfig;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateLimiterService {

  private final RateLimiterConfig rateLimiterConfig;

  public boolean tryConsume(String addrKey) {
    Bucket bucket = getOrCreateBucket(addrKey);
    ConsumptionProbe consumptionProbe = bucket.tryConsumeAndReturnRemaining(1);
    // 필요시 로깅
    handleNotConsume(consumptionProbe);
    return consumptionProbe.isConsumed();
  }

  public long getRemainToken(String apiKey) {
    Bucket bucket = getOrCreateBucket(apiKey);
    return bucket.getAvailableTokens();
  }

  private Bucket getOrCreateBucket(String key) {
    return rateLimiterConfig.lettceBasedProxyManager().builder()
        .build(key, rateLimiterConfig::bucketConfiguration);
  }

  private void handleNotConsume(ConsumptionProbe probe) {
    if (!probe.isConsumed()) {
      throw new BusinessException(ErrorCode.TOO_MANY_REQUEST);
    }
  }
}
