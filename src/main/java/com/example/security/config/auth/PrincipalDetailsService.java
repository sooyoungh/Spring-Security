package com.example.security.config.auth;


import com.example.security.config.auth.PrincipalDetails;
import com.example.security.domain.User;
import com.example.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Security Session (Authentication ( UserDetails ( PrincipalDetails 객체 ) ) ) )

// 시큐리티 설정에서 loginProcessingUrl("/login")
// /login 요청이 오면, 자동으로 PrincipalDetailsService 타입을 찾아서
// loadUserByUsername 메소드 실행!

@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // 이 메소드에서 로그인폼의 'username' 을 인자로 받음!
    // User 객체를 PrincipalDetails 에 주입해서 UserDetails 타입으로 반환! -> Authentication 에 주입
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username);
        if (userEntity != null) {
            return new PrincipalDetails(userEntity);
        }
        return null;
    }
}
