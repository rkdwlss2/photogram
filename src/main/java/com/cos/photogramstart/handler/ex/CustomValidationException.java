package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomValidationException extends RuntimeException{


    // 객체를 구분할때
    private static final long serialVersionUID =1L;

//    private String message;
    private Map<String,String> errorMap;

    public CustomValidationException(String message,Map<String,String> errorMap){
        super(message); //부모한테 던짐
//        this.message=message;
        this.errorMap=errorMap;
    }

    public Map<String,String> getErrorMap(){
        return errorMap;
    }


}
