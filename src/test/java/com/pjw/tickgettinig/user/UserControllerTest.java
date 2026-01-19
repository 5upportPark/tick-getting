package com.pjw.tickgettinig.user;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pjw.tickgettinig.common.GlobalExceptionHandler;
import com.pjw.tickgettinig.common.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

  private MockMvc mockMvc;

  @Mock
  private UserService userService;

  @InjectMocks
  private UserController userController;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(userController)
        .setControllerAdvice(new GlobalExceptionHandler())
        .build();
  }

  @Test
  @DisplayName("ID로 회원 정보를 조회하면 UserInfo를 반환한다.")
  void getUser_Success() throws Exception {
    // given
    Long userId = 1L;
    User user = User.builder()
        .id(userId)
        .username("testuser")
        .email("test@example.com")
        .build();
    given(userService.getUser(userId)).willReturn(user);

    // when & then
    mockMvc.perform(get("/v1/user")
            .param("id", userId.toString())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(userId))
        .andExpect(jsonPath("$.name").value("testuser"))
        .andExpect(jsonPath("$.email").value("test@example.com"));
  }

  @Test
  @DisplayName("존재하지 않는 ID로 회원 정보를 조회하면 404 에러를 반환한다.")
  void getUser_NotFound() throws Exception {
    // given
    Long userId = 99L;
    given(userService.getUser(userId)).willThrow(new UserNotFoundException("User Not Found"));

    // when & then
    mockMvc.perform(get("/v1/user")
            .param("id", userId.toString())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }
}
