package com.cos.photogramstart.handler;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@ControllerAdvice // 모든 exception 다 낚아챔
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class) //RuntimeException이 일어나는 모든 일에 캐칭
    public Map<String, String> validationException(CustomValidationException e){
        return e.getErrorMap(); //message를 리턴함
    }
}
