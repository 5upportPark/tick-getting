package com.pjw.tickgettinig.product.controller;

import com.pjw.tickgettinig.product.service.ProductService;
import com.pjw.tickgettinig.product.ProductView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/product")
public class ProductController {

  private final ProductService productService;

  @GetMapping
  public ResponseEntity<ProductView> getProductInfo(@RequestParam Long productId) {
    return new ResponseEntity<>(productService.getProduct(productId), HttpStatus.OK);
  }
}
