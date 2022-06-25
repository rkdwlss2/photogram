package com.cos.photogramstart.web.dto.auth;

import lombok.Data;

@Data //getter, setter 만들어주는 annotation;
public class SignupDto {
    private String username;
    private String password;
    private String email;
    private String name;

}
