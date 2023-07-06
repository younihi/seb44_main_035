package com.server.server.domain.oauth.info;

import com.server.server.domain.oauth.entity.ProviderType;
import com.server.server.domain.oauth.info.impl.FacebookOAuth2UserInfo;
import com.server.server.domain.oauth.info.impl.GoogleOAuth2UserInfo;
import com.server.server.domain.oauth.info.impl.KakaoOAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(ProviderType providerType, Map<String, Object> attributes) {
        switch (providerType) {
            case GOOGLE: return new GoogleOAuth2UserInfo(attributes);
            case FACEBOOK: return new FacebookOAuth2UserInfo(attributes);
            case KAKAO: return new KakaoOAuth2UserInfo(attributes);
            default: throw new IllegalArgumentException("Invalid Provider Type.");
        }
    }
}

