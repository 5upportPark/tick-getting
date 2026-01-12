package com.pjw.tickgettinig.user;

import com.pjw.tickgettinig.common.exceptions.UserNotFoundException;
import com.pjw.tickgettinig.oauth.dto.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;

  public void registerUser(User user) {
    userRepository.save(user);
  }

  public void editUser(User user) {
    if (user == null || user.getId() == null) {
      throw new UserNotFoundException("User not found: " + "id가 존재하지 않음");
    }
    userRepository.save(user);
  }

  public User getUser(String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException("User Not Found"));
    return user;
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

  public User getOrCreateUser(OAuth2UserInfo userInfo) {
    Optional<User> userOptional = userRepository.findByProviderAndProviderId(userInfo.getProvider(),
        userInfo.getProviderId());
    if (userOptional.isPresent()) {
      return userOptional.get();
    } else {
      User user = User.builder()
          .email(userInfo.getEmail())
          .username(userInfo.getName())
          .provider(userInfo.getProvider())
          .providerId(userInfo.getProviderId())
          .roles(List.of("ROLE_USER"))
          .state("A")
          .build();
      userRepository.save(user);
      return user;
    }
  }
}
