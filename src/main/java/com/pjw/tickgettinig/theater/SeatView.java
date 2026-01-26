package com.pjw.tickgettinig.theater;

import com.pjw.tickgettinig.theater.entity.Seat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatView {

  private Long id;
  private String position;
  private String grade;
  private boolean isAvailable;

  public static SeatView from(Seat seat) {
    return SeatView.builder()
        .id(seat.getId())
        .position(seat.getPosition())
        .grade(seat.getGrade())
        .build();
  }
}
