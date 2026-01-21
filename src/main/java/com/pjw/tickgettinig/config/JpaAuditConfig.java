package com.pjw.tickgettinig.config;

import com.pjw.tickgettinig.common.MemberIdAuditorAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class JpaAuditConfig {

  @Bean
  public AuditorAware<Long> auditorProvider() {
    return new MemberIdAuditorAware();
  }
}
