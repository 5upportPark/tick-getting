package com.pjw.tickgettinig.common;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MemberIdAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        // TODO: 토큰에서 memberId 조회
        return Optional.empty();
    }
}
