package com.pjw.tickgettinig.theater.entity;

import com.pjw.tickgettinig.entity.BaseInfoEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seat")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Seat extends BaseInfoEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String position;
  private String grade;
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "theater_id", referencedColumnName = "id")
  private Theater theater;

}
