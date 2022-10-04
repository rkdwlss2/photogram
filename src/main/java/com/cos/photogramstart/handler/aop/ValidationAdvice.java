package com.cos.photogramstart.handler.aop;


import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component //RestController, service component의 구현체
@Aspect
public class ValidationAdvice {


    @Around("execution(* com.cos.photogramstart.web.api.*Controller.*(..))") // *Contoller면 콘트롤러로 끝나는 모든 메소드안에 ..이면 파라미터 어떤것든 해당됨
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable { // ProceedingJoinPoint 모든 매개 변수에 접근할수있다.

        System.out.println("web api 컨트롤러=============");
        // proceedingJoinPoint profile함수의 모든 곳에 접근할 수 있는 변수
        // profile 함수보다 먼저 실행
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args){
            if (arg instanceof BindingResult){
                BindingResult bindingResult = (BindingResult) arg;
                if (bindingResult.hasErrors()){ //blank, username길이 넘어가면
                    Map<String,String> erroMap = new HashMap<>();
                    for (FieldError error:bindingResult.getFieldErrors()){
                        erroMap.put(error.getField(),error.getDefaultMessage());
//                System.out.println("======================================");
//                System.out.println(error.getDefaultMessage());
//                System.out.println("======================================");
                    }
                    throw new CustomValidationApiException("유효성 검사 실패함",erroMap);
//            throw new RuntimeException("유효성 검사 실패함");
//            return "오류남";  //오류가 나면 exception 발동
                }
            }
            System.out.println(arg);
        }
        return proceedingJoinPoint.proceed(); // profile 함수가 실행됨 그 함수로 다시 돌아가라
    }

    @Around("execution(* com.cos.photogramstart.web.*Controller.*(..))")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        System.out.println("web 컨트롤러=============");
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args){
            if (arg instanceof BindingResult){
                System.out.println("유효성 검사를 하는 함수입니다.");
                BindingResult bindingResult = (BindingResult) arg;

                if (bindingResult.hasErrors()){ //blank, username길이 넘어가면
                    Map<String,String> erroMap = new HashMap<>();
                    for (FieldError error:bindingResult.getFieldErrors()){
                        erroMap.put(error.getField(),error.getDefaultMessage());
                        System.out.println("======================================");
                        System.out.println(error.getDefaultMessage());
                        System.out.println("======================================");
                    }
                    throw new CustomValidationException("유효성 검사 실패함",erroMap);
//            throw new RuntimeException("유효성 검사 실패함");
//            return "오류남";  //오류가 나면 exception 발동
                }

            }
            System.out.println(arg);
        }
        return proceedingJoinPoint.proceed();
    }

}
