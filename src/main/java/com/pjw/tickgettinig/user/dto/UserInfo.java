package com.pjw.tickgettinig.user.dto;

import com.pjw.tickgettinig.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserInfo {

  private Long id;
  private String name;
  private String email;
  private String mobile;

  public static UserInfo of(User user) {
    return UserInfo.builder()
        .id(user.getId())
        .name(user.getName())
        .email(user.getEmail())
        .mobile(user.getMobile())
        .build();
  }
}
