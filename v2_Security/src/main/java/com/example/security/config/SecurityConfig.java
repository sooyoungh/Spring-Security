package com.example.security.config;

import com.example.security.config.oauth.PrincipalOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration // IoC 빈(bean)을 등록
@EnableWebSecurity // 해당 필터가 필터체인에 등록
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // 특정 주소 접근시 권한 및 인증을 위한 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter  {

    @Autowired
    private PrincipalOAuth2UserService principalOAuth2UserService;

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated() // 해당 주소면 인증 필요
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") // 해당 주소는 정해진 Role 만 가능!
                .anyRequest().permitAll() // 위 페이지들 외에는 다 입장가능
                /* 로그인 페이지 설정해주기! */
                .and()
                .formLogin()
                .loginPage("/loginForm") // 해당 페이지(로그인 페이지)로 이동!
                .loginProcessingUrl("/login") // 해당 주소가 호출되면, 시큐리티가 낚아채서 로그인 진행! -> 컨트롤러에 따로 만들지 않아도 됨
                .defaultSuccessUrl("/") // 로그인 성공시 여기로 이동
                /* 구글 로그인 설정 */
                .and()
                .oauth2Login()
                .loginPage("/loginForm")
                /* 구글 로그인 완료 후 후처리 */
                /* 코드 받기(인증) -> 엑세스 토큰(권한) -> 사용자 프로필 정보 가져옴 -> 이 정보로 회원가입 시킬 수 있음*/
                /* 로그인 완료 시, {토큰 + 프로필 정보}를 받음! */
                .userInfoEndpoint()
                .userService(principalOAuth2UserService);
    }
}