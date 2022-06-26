package com.cos.photogramstart.web;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor //final 필드를 DI 할때 씀
@Controller // 1. IoC등록 됨 2. 파일을 리턴하는 컨트롤러
public class AuthController {

//    public static final Logger log = LoggerFactory.getLogger(AuthController.class);

//    @Autowired // 첫번째
    private final AuthService authService; // 전역변수에 final 걸려있으면 초기화 시켜줘야 해서 오류 생김

    // 생성자 주입
//    public AuthController(AuthService authService){ // IOC에 등록 되어 있어서 먼저 찾아서 주입해줌 의존성 주입 매우 귀찮음
//        this.authService = authService;
//    }

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
    @PostMapping("/auth/signup")  // 오류 모아둠 getFieldErrors collection에 모아둠
    public @ResponseBody String signup(@Valid SignupDto signupDto, BindingResult bindingResult){ // key=value 형식으로 들어옴 (x-www-form-urlencoded 방식)
        if (bindingResult.hasErrors()){ //blank, username길이 넘어가면
            Map<String,String> erroMap = new HashMap<>();
            for (FieldError error:bindingResult.getFieldErrors()){
                erroMap.put(error.getField(),error.getDefaultMessage());
                System.out.println("======================================");
                System.out.println(error.getDefaultMessage());
                System.out.println("======================================");
            }
            return "오류남";
        }else{
            User user = signupDto.toEntity();
            User userEntity = authService.회원가입(user);
            System.out.println(userEntity);
            return "auth/signin"; //회원가입이 성공하면 로그인 페이지로 이동

        }
//
//        //        if (signupDto.getUsername().length()>20){  //validation 체크
////            System.out.printf("너 길이 초과했어");
////        }
//
//        //        System.out.println("sign up 실행됨?");
////        log.info(signupDto.toString());
//        //User -> signup Dto에 넣을꺼임임
//        User user = signupDto.toEntity();
////        log.info(user.toString());
//        User userEntity = authService.회원가입(user);
//        System.out.println(userEntity);
//       return "auth/signin"; //회원가입이 성공하면 로그인 페이지로 이동
    }
}
