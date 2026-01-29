package com.pjw.tickgettinig.service;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "TEST", description = "redis 대기열 테스트용")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/queue")
public class QueueController {

  private final WaitingQueueService waitingQueueService;

  @GetMapping
  public ResponseEntity<Object> registerQueue(@RequestParam Long productId, @RequestParam Long userId) {
    return new ResponseEntity<>(waitingQueueService.registerQueue(productId, userId), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Object> allowEntry(@RequestParam Long productId) {
    return new ResponseEntity<>(waitingQueueService.allowEntry(productId), HttpStatus.OK);
  }
}
