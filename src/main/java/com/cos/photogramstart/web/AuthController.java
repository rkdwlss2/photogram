package com.cos.photogramstart.web;

import com.cos.photogramstart.web.dto.auth.SignupDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller // 1. IoC등록 됨 2. 파일을 리턴하는 컨트롤러
public class AuthController {

    public static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/auth/signin")
    public String signinForm(){
        return "auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signupForm(){
        return "auth/signup";
    }

    //회원가입 -> /auth/signup -> /auth/signin 리턴됨
    // 회원가입 클릭했는데 아무것도 안됨
    @PostMapping("/auth/signup")
    public String signup(SignupDto signupDto){ // key=value 형식으로 들어옴 (x-www-form-urlencoded 방식)
//        System.out.println("sign up 실행됨?");
        log.info(signupDto.toString());
        return "auth/signin"; //회원가입이 성공하면 로그인 페이지로 이동
    }
}
