package com.pjw.tickgettinig.oauth.controller;

import com.pjw.tickgettinig.account.LoginResponse;
import com.pjw.tickgettinig.jwt.JwtProvider;
import com.pjw.tickgettinig.oauth.SnsType;
import com.pjw.tickgettinig.oauth.client.OAuth2Client;
import com.pjw.tickgettinig.oauth.dto.NaverResponse;
import com.pjw.tickgettinig.oauth.dto.OAuth2UserInfo;
import com.pjw.tickgettinig.user.User;
import com.pjw.tickgettinig.user.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "SNS 로그인 API", description = "")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/oauth2")
public class OAuth2Controller {

  private final OAuth2Client oAuth2Client;
  private final UserService userService;
  private final JwtProvider jwtProvider;

  @PostMapping("/naver")
  public ResponseEntity<LoginResponse> naverLoginCallback(@RequestBody NaverResponse response) {
    OAuth2UserInfo userInfo = oAuth2Client.getUserInfo(SnsType.NAVER, response.getCode());
    User user = userService.getOrCreateUser(userInfo, SnsType.NAVER);
    LoginResponse result = LoginResponse.of(jwtProvider.getAccessToken(user.getUsername(), userInfo.getProviderId()), null);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}
