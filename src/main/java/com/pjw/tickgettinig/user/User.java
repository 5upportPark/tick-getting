package com.pjw.tickgettinig.user;

import com.pjw.tickgettinig.converter.StringListConverter;
import com.pjw.tickgettinig.entity.BaseInfoEntity;
import com.pjw.tickgettinig.oauth.SnsUser;
import com.pjw.tickgettinig.user.dto.UserRequest;
import jakarta.persistence.*;
import java.util.Collection;
import lombok.*;
import lombok.Builder.Default;
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
public class User extends BaseInfoEntity implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String username;
  private String password;
  private String name;
  @Column(nullable = false, unique = true)
  private String email;
  @Column(nullable = false, length = 1)
  private String state;

  private String provider;
  private String providerId;
  protected LocalDateTime lastLoginAt;

  @Column(nullable = false)
  @Convert(converter = StringListConverter.class)
  @Default
  private List<String> roles = new ArrayList<>();

  @OneToOne(mappedBy = "user")
  private SnsUser snsUser;
  // 연결된 sns_user_map이 있는지 확인하기 위해 즉시로딩으로만 동작함(N+1문제)

  public static User from(UserRequest.Regist req) {
    return User.builder()
        .username(req.getUsername())
        .password(req.getPassword())
        .name(req.getName())
        .email(req.getEmail())
        .state("A")
        .roles(List.of("USER"))
        .build();
  }

  public void withdraw() {
    this.updatedAt = LocalDateTime.now();
    this.updatedBy = username;
  }

  public User updateLastLoginAt() {
    this.lastLoginAt = LocalDateTime.now();
    return this;
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
