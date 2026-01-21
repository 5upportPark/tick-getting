package com.pjw.tickgettinig.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.pjw.tickgettinig.common.exceptions.UserNotFoundException;
import com.pjw.tickgettinig.jwt.JwtProvider;
import com.pjw.tickgettinig.user.dto.UserInfo;
import com.pjw.tickgettinig.user.repository.SnsUserRepository;
import com.pjw.tickgettinig.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private SnsUserRepository snsUserRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private JwtProvider jwtProvider;

  @InjectMocks
  private UserService userService;

  @Test
  @DisplayName("ID로 회원 정보를 조회할 수 있다.")
  void getUserById_Success() {
    // given
    Long userId = 1L;
    User user = User.builder()
        .id(userId)
        .name("testuser")
        .email("test@example.com")
        .build();
    given(userRepository.findById(userId)).willReturn(Optional.of(user));

    // when
    UserInfo result = userService.getUser(userId);

    // then
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(userId);
    assertThat(result.getName()).isEqualTo("testuser");
  }

  @Test
  @DisplayName("존재하지 않는 ID로 회원 정보 조회 시 예외가 발생한다.")
  void getUserById_UserNotFound() {
    // given
    Long userId = 1L;
    given(userRepository.findById(userId)).willReturn(Optional.empty());

    // when & then
    assertThatThrownBy(() -> userService.getUser(userId))
        .isInstanceOf(UserNotFoundException.class)
        .hasMessageContaining("User Not Found");
  }
}
