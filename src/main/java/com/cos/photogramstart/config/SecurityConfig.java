package com.cos.photogramstart.config;

import com.cos.photogramstart.config.oauth.OAuth2DetailsSerivce;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity //해당 파일로 시큐리티를 활성화
@Configuration //ioc
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final OAuth2DetailsSerivce oAuth2DetailsSerivce;

    @Bean // SecurityConfig 가 IOC에 등록될때 Bean annotaion을 읽어서
    public BCryptPasswordEncoder encode(){
        return new BCryptPasswordEncoder(); //이걸 return해서 DI에서 써먹기만 하면됨
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http); //가로채는중 super 삭제 기존 시큐리티가 가지고 있는 기능이 비활성화 됨
        //인증 안간 곳은 login 페이지로 이동
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/","/user/**","/image/**","/subscribe/**","/comment/**","/api/**")
                .authenticated()// 인증 필요해
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/auth/signin") //Get
                .loginProcessingUrl("/auth/signin")//post -> spring security가 로그인 프로세스를 진행함
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login() // form 로그인도 하는데 oauth2로그인도 할꺼야
                .userInfoEndpoint() // oauth2 로그인을 하면 최증 응답을 회원정보를 바로 받을 수 있다.
                .userService(oAuth2DetailsSerivce)
                ;
    }
}
