package com.pjw.tickgettinig.common;

import com.pjw.tickgettinig.user.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class MemberIdAuditorAware implements AuditorAware<Long> {

  @Override
  public Optional<Long> getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
      return Optional.empty();
    }
    Object principal = authentication.getPrincipal();
    if (principal instanceof User) {
      return Optional.ofNullable(((User) principal).getId());
    }
    return Optional.empty();
  }
}
