package com.pjw.tickgettinig.oauth;

import com.pjw.tickgettinig.entity.BaseInfoEntity;
import com.pjw.tickgettinig.user.User;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "sns_user_map")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SnsUser extends BaseInfoEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Fetch(FetchMode.JOIN)
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  private User user;
  @Convert(converter = SnsTypeConverter.class)
  private SnsType snsType;
  private String userName;
  private String email;
  private String accessToken;
  private String refreshToken;

  public static SnsUser of(User user, SnsType snsType, String userName, String email, String accessToken, String refreshToken) {
    return SnsUser.builder()
        .user(user)
        .snsType(snsType)
        .userName(userName)
        .email(email)
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }
}
