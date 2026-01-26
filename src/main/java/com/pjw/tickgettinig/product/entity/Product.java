package com.pjw.tickgettinig.product.entity;

import com.pjw.tickgettinig.entity.BaseInfoEntity;
import com.pjw.tickgettinig.theater.entity.Theater;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Product extends BaseInfoEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String description;
  private LocalDateTime startDate;
  private LocalDateTime endDate;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "theater_id", referencedColumnName = "id")
  private Theater theater;
}
