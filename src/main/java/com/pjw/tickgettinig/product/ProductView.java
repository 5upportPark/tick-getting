package com.pjw.tickgettinig.product;

import com.pjw.tickgettinig.theater.TheaterView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ProductView {

  private Long id;
  private String name;
  private String description;
  private Double price;

  private TheaterView theater;
}
