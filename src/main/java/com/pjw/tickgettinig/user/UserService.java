package com.pjw.tickgettinig.user;

import com.pjw.tickgettinig.account.LoginRequest;
import com.pjw.tickgettinig.account.LoginResponse;
import com.pjw.tickgettinig.common.exceptions.UserNotFoundException;
import com.pjw.tickgettinig.jwt.JwtProvider;
import com.pjw.tickgettinig.oauth.SnsType;
import com.pjw.tickgettinig.oauth.SnsUser;
import com.pjw.tickgettinig.oauth.dto.OAuth2UserInfo;
import com.pjw.tickgettinig.user.dto.UserRequest;
import com.pjw.tickgettinig.user.repository.SnsUserRepository;
import com.pjw.tickgettinig.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final SnsUserRepository snsUserRepository;

  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;

  public void registerUser(UserRequest.Regist req) {
    req.setPassword(passwordEncoder.encode(req.getPassword()));

    User user = User.from(req);
    userRepository.save(user);
  }

  public void editUser(UserRequest.Edit req) {
    if (req == null || req.getId() == null) {
      throw new UserNotFoundException("User not found: " + "id가 존재하지 않음");
    }
    User user = userRepository.findById(req.getId()).orElseThrow(UserNotFoundException::new);
    user.updateFromRequest(req);
    userRepository.save(user);
  }

  public User getUser(Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("User Not Found"));
    return user;
  }

  public User getUser(String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException("User Not Found"));
    return user;
  }

  public LoginResponse login(LoginRequest loginRequest) {
    User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new UserNotFoundException("User Not Found"));
    if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
      throw new UserNotFoundException("Wrong password");
    }
    userRepository.save(user.updateLastLoginAt());
    return LoginResponse.of(jwtProvider.getAccessToken(user.getUsername(), user.getId()), jwtProvider.getRefreshToken(user.getUsername()));
  }

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username)
        .orElseThrow(
            () -> new UsernameNotFoundException("User not found with username: " + username));
  }

  /**
   * 회원 탈퇴
   *
   * @param username
   */
  public void withdrawUser(String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException("User not found"));
    user.withdraw();
    userRepository.save(user);
  }

  public User getOrCreateUser(OAuth2UserInfo userInfo, SnsType snsType) {
    Optional<User> userOptional = userRepository.findByProviderAndProviderId(userInfo.getProvider(),
        userInfo.getProviderId());
    if (userOptional.isPresent()) {
      return userOptional.get();
    } else {

      User user = User.builder()
          .username(userInfo.getEmail())
          .email(userInfo.getEmail())
          .name(userInfo.getName())
          .provider(userInfo.getProvider())
          .providerId(userInfo.getProviderId())
          .roles(List.of("USER"))
          .state("A")
          .build();
      userRepository.save(user);

      SnsUser snsUserInfo = SnsUser.of(user, snsType, userInfo.getEmail(), userInfo.getEmail(), userInfo.getAccessToken(),
          userInfo.getRefreshToken());
      snsUserRepository.save(snsUserInfo);
      return user;
    }
  }

}
