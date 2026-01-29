package com.pjw.tickgettinig.service;

import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WaitingQueueService {

  private final RedisService redisService;

  private static final int MAX_COUNT = 1000;

  // 대기열 진입
  //@ApiRateLimit(key = "enter-queue")
  public Long registerQueue(Long productId, Long userId) {
    String waitingKey = QueueKey.WAIT.of(productId);
    Long count = redisService.getZSetTotalCount(waitingKey);
    System.out.println("Waiting queue count: " + count + ", add: " + waitingKey);
    // 대기열 추가
    redisService.addZSetTimeScore(waitingKey, userId);
    // 대기번호 subscribe~~
    return redisService.getZSetRank(waitingKey, userId); // 대기 번호 조회
  }

  //TODO 대기번호 메시징으로 전달?
  public Long sendWaitingNumber(Long productId, Long userId) {
    return redisService.getZSetRank(QueueKey.WAIT.of(productId), userId);
  }

  // 진입 허용
  public List<String> allowEntry(Long productId) {
    String waitingKey = QueueKey.WAIT.of(productId);
    Set<String> entryUsers = redisService.getZSetRange(waitingKey, MAX_COUNT); // 0번째부터 count-1까지 조회
    System.out.println("entryUsers: " + entryUsers);
    if (entryUsers != null && !entryUsers.isEmpty()) {
      String[] users = entryUsers.toArray(new String[0]);
      // 통과된 사용자는 '진입 허용' 상태로 변경
      redisService.addSet(QueueKey.PROC.of(productId), users);
      redisService.removeZSet(waitingKey, users);
      return entryUsers.stream().toList();
    }
    return null;
  }

  public void removeInProcessQueue(Long productId, Long userId) {
    redisService.removeSet(QueueKey.PROC.of(productId), String.valueOf(userId));
  }

  public boolean isInWaiting(Long productId, Long userId) {
    return redisService.hasValueZSet(QueueKey.WAIT.of(productId), userId);
  }

  public boolean isInProcessing(Long productId, Long userId) {
    return redisService.hasValueSet(QueueKey.PROC.of(productId), userId);
  }
}
