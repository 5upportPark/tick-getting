package com.pjw.tickgettinig.theater;

import com.pjw.tickgettinig.theater.entity.Theater;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TheaterView {

  private Long id;
  private String name;
  private String address;
  private List<SeatView> seats;

  public static TheaterView from(Theater theater) {
    List<SeatView> seatViews = new ArrayList<>();
    if (theater.getSeats() != null && !theater.getSeats().isEmpty()) {
      seatViews = theater.getSeats().stream().map(SeatView::from).toList();
    }
    return TheaterView.builder()
        .id(theater.getId())
        .name(theater.getName())
        .address(theater.getAddress())
        .seats(seatViews)
        .build();
  }
}
