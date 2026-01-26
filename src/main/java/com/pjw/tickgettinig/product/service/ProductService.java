package com.pjw.tickgettinig.product.service;

import com.pjw.tickgettinig.common.ErrorCode;
import com.pjw.tickgettinig.common.exceptions.BusinessException;
import com.pjw.tickgettinig.product.ProductView;
import com.pjw.tickgettinig.product.entity.Product;
import com.pjw.tickgettinig.product.repository.ProductRepository;
import com.pjw.tickgettinig.theater.SeatView;
import com.pjw.tickgettinig.theater.TheaterView;
import com.pjw.tickgettinig.theater.entity.Theater;
import com.pjw.tickgettinig.theater.repository.TheaterRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final TheaterRepository theaterRepository;

  public ProductView getProduct(Long productId) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new BusinessException("공연 정보가 존재하지 않습니다.", ErrorCode.RESOURCE_NOT_FOUND));
    Theater theater = theaterRepository.findById(product.getTheater().getId())
        .orElseThrow(() -> new BusinessException("극장 정보가 존재하지 않습니다.", ErrorCode.RESOURCE_NOT_FOUND));
    List<SeatView> seats = theater.getSeats().stream().map(SeatView::from).toList();
    TheaterView theaterView = TheaterView.from(theater);

    return ProductView.builder()
        .id(productId)
        .theater(theaterView)
        .build();
  }
}
