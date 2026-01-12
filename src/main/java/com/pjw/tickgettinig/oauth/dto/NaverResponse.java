package com.pjw.tickgettinig.oauth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NaverResponse {
    private String id;
    private String nickname;
    private String email;

    private String code;
    private String state;
}
