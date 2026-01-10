package com.pjw.tickgettinig.config.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "oauth2.client")
public class OAuth2Properties {
    private final Properties naver;

    public OAuth2Properties(Properties naver) {
        this.naver = naver;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Properties {
        private final String clientId;
        private final String clientSecret;
        private final String redirectUri;
        private final String tokenUri;
        private final String userInfoUri;
        private final String authorizeUri;

    }
}
