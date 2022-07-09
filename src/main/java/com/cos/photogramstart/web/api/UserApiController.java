package com.cos.photogramstart.web.api;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;


    @PutMapping("/api/user/{id}")
    public CMRespDto<?> update(@PathVariable int id,
                               @Valid UserUpdateDto userUpdateDto,
                               BindingResult bindingResult,
                               @AuthenticationPrincipal PrincipalDetails principalDetails){

        if (bindingResult.hasErrors()){ //blank, username길이 넘어가면
            Map<String,String> erroMap = new HashMap<>();
            for (FieldError error:bindingResult.getFieldErrors()){
                erroMap.put(error.getField(),error.getDefaultMessage());
                System.out.println("======================================");
                System.out.println(error.getDefaultMessage());
                System.out.println("======================================");
            }
            throw new CustomValidationApiException("유효성 검사 실패함",erroMap);
//            throw new RuntimeException("유효성 검사 실패함");
//            return "오류남";  //오류가 나면 exception 발동
        }else{
            User userEntity = userService.회원수정(id,userUpdateDto.toEntity());
            principalDetails.setUser(userEntity);
            return new CMRespDto<>(1,"회원수정완료",userEntity);
        }

//        System.out.println(userUpdateDto);

    }
}
