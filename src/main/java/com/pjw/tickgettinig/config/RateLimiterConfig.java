package com.pjw.tickgettinig.config;

import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.BucketProxy;
import io.github.bucket4j.distributed.ExpirationAfterWriteStrategy;
import io.github.bucket4j.distributed.proxy.ClientSideConfig;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.redis.lettuce.cas.LettuceBasedProxyManager;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.StringCodec;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RateLimiterConfig {

  //private final ProxyManager proxyManager;

  private static final int CAPACITY = 10;
  private static final Duration TIMEOUT = Duration.ofSeconds(10);
  private final RedisClient redisClient;

/*  public boolean isAllowed(String key, int limit, int durationSeconds) {
    BucketConfiguration bucketConfiguration = BucketConfiguration.builder()
        .addLimit(bucketLimit ->
            bucketLimit.capacity(limit).refillIntervally(limit, Duration.ofSeconds(durationSeconds)))
        .build();
    //BucketProxy bucket = proxyManager.builder().build(key, bucketConfiguration);
    BucketProxy bucket = proxyManager.builder().build(key, () -> bucketConfiguration);
    return bucket.tryConsumeAndReturnRemaining(1).isConsumed();
  }*/

  @Bean
  public ProxyManager<String> lettceBasedProxyManager() {
    // 직렬화 설정
    StatefulRedisConnection<String, byte[]> redisConnection =
        redisClient.connect(RedisCodec.of(StringCodec.UTF8, ByteArrayCodec.INSTANCE));
    ClientSideConfig clientSideConfig = ClientSideConfig.getDefault()
        .withExpirationAfterWriteStrategy(ExpirationAfterWriteStrategy.basedOnTimeForRefillingBucketUpToMax(Duration.ofSeconds(10L)));

    return LettuceBasedProxyManager.builderFor(redisConnection)
        .withClientSideConfig(clientSideConfig).build();
  }

  @Bean
  public BucketConfiguration bucketConfiguration() {
    return BucketConfiguration.builder()
        .addLimit(bucketLimit ->
            bucketLimit.capacity(CAPACITY).refillIntervally(CAPACITY, TIMEOUT))
        .build();
  }

}
