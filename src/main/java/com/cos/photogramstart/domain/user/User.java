package com.cos.photogramstart.domain.user;

//JPA - Java Persistence API java로 데이터를 영구적으로(DB) 저장할 수 있는 API를 제공해줌

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


// table 생성됨 ORM OBject 기준으로 테이블 만들어짐
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity // DB에 테이블을 생성 해줌
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //번호 증가 전략이 데이터 베이스를 따라 갑니다.
    private int id; // 서비스 하는 프로그램이 아니라서 int로도 충분함

    @Column(length = 20,unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    private String website; // 웹사이트
    private String bio; // 자기 소개
    @Column(nullable = false)
    private String email;
    private String phone;
    private String gender;

    private String profileImageUrl; // 작성자 사진

    private String role; // 권한

    private LocalDateTime createDate;

    //자동으로 들어가게 할꺼여서

    @PrePersist // 디비에 INSERT 되기 직전에 실행됨
    public void createDate(){
        this.createDate=LocalDateTime.now();
    }
}
