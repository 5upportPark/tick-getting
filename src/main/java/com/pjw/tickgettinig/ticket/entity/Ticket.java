package com.pjw.tickgettinig.ticket.entity;

import com.pjw.tickgettinig.entity.BaseInfoEntity;
import com.pjw.tickgettinig.product.entity.Product;
import com.pjw.tickgettinig.theater.entity.Seat;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "ticket_info")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket extends BaseInfoEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String description;
  private BigDecimal price;
  private Integer quantity;
  private Integer discount;
  private BigDecimal totalPrice;
  private LocalDate scheduleDate;
  private LocalTime scheduleTime;
  private String status;
  private Long userId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", referencedColumnName = "id")
  private Product product;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "seat_id", referencedColumnName = "id")
  private Seat seat;

  public void reservation(Long userId) {
    this.userId = userId;
  }
}
