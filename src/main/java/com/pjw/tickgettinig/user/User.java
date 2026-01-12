package com.pjw.tickgettinig.user;

import com.pjw.tickgettinig.converter.StringListConverter;
import com.pjw.tickgettinig.entity.BaseInfoEntity;
import jakarta.persistence.*;
import java.util.Collection;
import lombok.*;
import lombok.Builder.Default;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@EntityListeners(value = {AuditingEntityListener.class})
public class User extends BaseInfoEntity implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String username;
  private String password;
  @Column(nullable = false, unique = true)
  private String email;
  @Column(nullable = false, length = 1)
  private String state;

  private String provider;
  private String providerId;

  @Column(nullable = false)
  @Convert(converter = StringListConverter.class)
  @Default
  private List<String> roles = new ArrayList<>();

  public static User of(String auth) {
    return User.builder()
        .state("A")
        .roles(List.of(auth))
        .build();
  }

  public void withdraw() {
    this.updatedAt = LocalDateTime.now();
    this.updatedBy = username;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles.stream().map(Authority::new).toList();
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }
}
