package com.example.security.config.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService {

    // 구글 로그인 후 후처리
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("userRequest : " + userRequest.getClientRegistration()); // 서버 정보) 구글 api 등록한 클라이언트 id, 키값 정보
        System.out.println("getAccessToken : " + userRequest.getAccessToken()); // 토큰값
        System.out.println("getAuthorities : " + super.loadUser(userRequest).getAuthorities()); // 구글 로그인 정보, 필요한 정보!

        // 구글에서 회원 프로필 정보 받기
        OAuth2User oauth2User = super.loadUser(userRequest);

        return super.loadUser(userRequest);
    }
}
