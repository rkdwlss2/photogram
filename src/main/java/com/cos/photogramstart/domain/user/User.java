package com.cos.photogramstart.domain.user;

//JPA - Java Persistence API java로 데이터를 영구적으로(DB) 저장할 수 있는 API를 제공해줌

import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


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

    @Column(length = 100,unique = true) // oAuth2 로그인을 통해 칼럼 늘리기
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", website='" + website + '\'' +
                ", bio='" + bio + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                ", role='" + role + '\'' +
                ", createDate=" + createDate +
                '}';
    }

    //Db에 넣지말라는걸 알려 줘야함
    // User 를 select할때 해당 User id로 등록된 image들을 다 가져와
    // fetchtype.LAZY 해당 된 이미지를 다 가져와가 아니라
    // LAZY = User 를 select할때 해당 USer id 로 등록된 image들을 가져오지마 -> 대신 get images들이 호출 될때 가져와
    // EAGER = User를 select 할때 해당 USER id 로 등록된 image들을 전부 JOIN해서 가져와!!
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY) // mappedby 나는 연관관계의 주인이 이다. 그럼으로 테이블에 컬럼을 만들지마
    @JsonIgnoreProperties({"user"})
    private List<Image> images;

    //자동으로 들어가게 할꺼여서

    @PrePersist // 디비에 INSERT 되기 직전에 실행됨
    public void createDate(){
        this.createDate=LocalDateTime.now();
    }
}
