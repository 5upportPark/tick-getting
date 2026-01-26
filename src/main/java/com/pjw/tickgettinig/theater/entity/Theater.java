package com.pjw.tickgettinig.theater.entity;

import com.pjw.tickgettinig.entity.BaseInfoEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "theater")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Theater extends BaseInfoEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String address;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "theater")
  private List<Seat> seats;
}
