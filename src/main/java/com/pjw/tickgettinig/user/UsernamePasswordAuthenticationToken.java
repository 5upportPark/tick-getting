package com.pjw.tickgettinig.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

// FIXME 정리필요
public class UsernamePasswordAuthenticationToken implements Authentication {

  private User user;
  private Collection<? extends GrantedAuthority> authorities;
  private Boolean isAuthenticated;

  public static UsernamePasswordAuthenticationToken of(User user) {
    return new UsernamePasswordAuthenticationToken(user, user.getAuthorities());
  }

  private UsernamePasswordAuthenticationToken(User user, Collection<? extends GrantedAuthority> authorities) {
    this.user = user;
    this.authorities = authorities;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getDetails() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return user;
  }

  @Override
  public boolean isAuthenticated() {
    return isAuthenticated;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    this.isAuthenticated = isAuthenticated;
  }

  @Override
  public String getName() {
    return user.getUsername();
  }
}
