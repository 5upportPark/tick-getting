package com.pjw.tickgettinig.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

// FIXME 정리필요
public class UsernamePasswordAuthenticationToken implements Authentication, UserDetails {

  private UserDetails user;
  private String username;
  private String password;
  private Collection<? extends GrantedAuthority> authorities;

  public static UsernamePasswordAuthenticationToken of(UserDetails user, String username, Collection<? extends GrantedAuthority> authorities) {
    return new UsernamePasswordAuthenticationToken(user, username, null, authorities);
  }

  private UsernamePasswordAuthenticationToken(UserDetails user, String username, String password,
      Collection<? extends GrantedAuthority> authorities) {
    this.user = user;
    this.username = user.getUsername();
    this.password = password;
    this.authorities = authorities;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
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
    return null;
  }

  @Override
  public boolean isAuthenticated() {
    return false;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

  }

  @Override
  public String getName() {
    return username;
  }
}
