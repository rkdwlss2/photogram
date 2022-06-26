package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity //해당 파일로 시큐리티를 활성화
@Configuration //ioc
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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
                .antMatchers("/","/user/**","/image/**","/subscribe/**","/comment/**")
                .authenticated()// 인증 필요해
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/auth/signin")
                .defaultSuccessUrl("/");
    }
}
