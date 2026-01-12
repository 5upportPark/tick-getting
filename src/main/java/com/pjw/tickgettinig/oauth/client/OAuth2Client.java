package com.pjw.tickgettinig.oauth.client;

import com.pjw.tickgettinig.oauth.SnsType;
import com.pjw.tickgettinig.oauth.dto.OAuth2UserInfo;

public interface OAuth2Client {
    OAuth2UserInfo getUserInfo(SnsType snsType, String code);
}
