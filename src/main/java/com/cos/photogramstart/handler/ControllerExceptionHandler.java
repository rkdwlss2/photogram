package com.cos.photogramstart.handler;

import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@ControllerAdvice // 모든 exception 다 낚아챔
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class) //RuntimeException이 일어나는 모든 일에 캐칭
    public String validationException(CustomValidationException e){
        // CMRespDto, Script 비교
        // 1. 클라이언트에게 응답할때는 Script  좋음.
        // 2. Ajax 통신 - CMRespDto
        // 3. Android 통신 - CMRespDto
        if (e.getErrorMap()==null){
            return Script.back(e.getMessage());
        }else{
            return Script.back(e.getErrorMap().toString());
        }

//        return new CMRespDto<Map<String,String>>(-1,e.getMessage(),e.getErrorMap()); //message를 리턴함
    }

    @ExceptionHandler(CustomValidationApiException.class) //RuntimeException이 일어나는 모든 일에 캐칭
    public ResponseEntity<?> validationApiException(CustomValidationApiException e){
        System.out.println("==================================나 실행됨==============================");
        // CMRespDto, Script 비교
        // 1. 클라이언트에게 응답할때는 Script  좋음.
        // 2. Ajax 통신 - CMRespDto
        // 3. Android 통신 - CMRespDto

        return new ResponseEntity<CMRespDto<?>>(new CMRespDto<>(-1,e.getMessage(),e.getErrorMap()),HttpStatus.BAD_REQUEST);
//        return new CMRespDto<Map<String,String>>(-1,e.getMessage(),e.getErrorMap()); //message를 리턴함
    }


    @ExceptionHandler(CustomApiException.class) //RuntimeException이 일어나는 모든 일에 캐칭
    public ResponseEntity<?> apiException(CustomApiException e){
        System.out.println("==================================나 실행됨==============================");
        // CMRespDto, Script 비교
        // 1. 클라이언트에게 응답할때는 Script  좋음.
        // 2. Ajax 통신 - CMRespDto
        // 3. Android 통신 - CMRespDto


        System.out.println("나 발동 되냐??????????????????");
        return new ResponseEntity<>(new CMRespDto<>(-1,e.getMessage(),null),HttpStatus.BAD_REQUEST);
//        return new CMRespDto<Map<String,String>>(-1,e.getMessage(),e.getErrorMap()); //message를 리턴함
    }
}
