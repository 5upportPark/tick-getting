package com.pjw.tickgettinig.aop;

import com.pjw.tickgettinig.common.ErrorCode;
import com.pjw.tickgettinig.common.exceptions.BusinessException;
import com.pjw.tickgettinig.service.RedisService;
import com.pjw.tickgettinig.service.WaitingQueueService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ApiRateLimitAspect {

  private final WaitingQueueService waitingQueueService;

  @Around("@annotation(apiRateLimit)")
  public Object rateLimit(ProceedingJoinPoint joinPoint, ApiRateLimit apiRateLimit) throws Throwable {
    // TODO bucket4j로 토큰 확인~~
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    String[] parameters = signature.getParameterNames();
    Object[] args = joinPoint.getArgs();

    Long productId = (Long) args[0];
    Long userId = (Long) args[1];
    // 대기 or 진행중인지 확인
    boolean isAvaliable = waitingQueueService.isInWaiting(productId, userId) || waitingQueueService.isInProcessing(productId, userId);
    if (isAvaliable) {
      return joinPoint.proceed();
    } else {
      waitingQueueService.registerQueue(productId, userId);
      throw new BusinessException(ErrorCode.WAIT_WARNING);
    }
  }

}
