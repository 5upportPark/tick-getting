package com.pjw.tickgettinig.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OAuthCustomService {

  private final AuthenticationManager authenticationManager;

  public UserDetails login(String username, String password) throws Exception {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password)
      );
      return (UserDetails) authentication.getPrincipal();
    } catch (Exception e) {
      throw new Exception("Authentication failed: " + e.getMessage());
    }
  }

  public boolean isAuthenticated(Authentication authentication) {
    return authentication != null && authentication.isAuthenticated();
  }
}

