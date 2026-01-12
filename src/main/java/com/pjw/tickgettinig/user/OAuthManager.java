package com.pjw.tickgettinig.user;

import com.pjw.tickgettinig.common.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OAuthManager implements AuthenticationManager {

  private final UserService userService;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getName();

    UserDetails userDetails;
    try {
      userDetails = userService.loadUserByUsername(username);
    } catch (UsernameNotFoundException e) {
      throw new UserNotFoundException("사용자를 찾을 수 없습니다: " + username);
    }

    // TODO: OAuth 토큰 유효성 검사 로직 추가 (예: 토큰 만료, 유효성 등)
    // 현재는 UserDetails가 성공적으로 로드되면 인증된 것으로 간주합니다.
    // 실제 OAuth 구현에서는 토큰의 유효성을 검증하는 과정이 필요합니다.

    // 인증된 Authentication 객체를 반환합니다. 비밀번호는 필요하지 않으므로 null로 설정합니다.
    return UsernamePasswordAuthenticationToken.of(userDetails, null, userDetails.getAuthorities());
  }
}
