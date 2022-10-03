package com.example.security.config.auth;


// 시큐리티가 /login 주소 낚아채서 로그인 진행시킴
// 로그인 완료되면 시큐리티 session 을 만들어줌
// 시큐리티의 세션에 들어감
// Security Context Holder 에 키값 (세션 정보) 저장

import com.example.security.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// 객체 위계
// Security Session (Authentication ( UserDetails ( PrincipalDetails 객체 ) ) ) )
// 여기서 PrincipalDetails 주입받아 UserDetails 객체까지 만듦
public class PrincipalDetails implements UserDetails {

    // 입력 받은 유저 정보
    private User user; // 컴포지션

    public PrincipalDetails (User user) {
        this.user = user;
    }

    // 해당 User 의 권한을 리턴!
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 계정 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 휴먼 계정 여부(ex 1년 이내 사용 X)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
