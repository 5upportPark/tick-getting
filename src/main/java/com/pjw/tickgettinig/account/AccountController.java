package com.pjw.tickgettinig.account;

import com.pjw.tickgettinig.jwt.JwtProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "사용자 계정 생성, 로그인, 로그아웃 관리 API", description = "")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/account")
public class AccountController {
    private final JwtProvider jwtProvider;

    @Operation(summary = "사용자 로그인")
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return LoginResponse.of(jwtProvider.getAccessToken(loginRequest.getId()), jwtProvider.getRefreshToken(loginRequest.getId()));
    }

}
