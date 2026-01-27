package com.pjw.tickgettinig.ticket.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class ReserveRequest {

  @Getter
  @AllArgsConstructor
  @Builder
  public static class Reservation {

    private Long productId;
    private Long seatId;
    private LocalDate scheduleDate;
    private LocalTime scheduleTime;
  }
}
