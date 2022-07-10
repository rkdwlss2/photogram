package com.cos.photogramstart.domain.image;

import com.cos.photogramstart.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity // DB에 테이블을 생성 해줌
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //번호 증가 전략이 데이터 베이스를 따라 갑니다.
    private int id; // 서비스 하는 프로그램이 아니라서 int로도 충분함

    private String caption; // 오늘 나 너무 피곤해!!
    private String postImageUrl; // 파일이 아님 사진을 전송 받아서 그 사진을 서버에 특정 폴더에 저장을 할꺼임 데이터 베이스에는 저장된 경로를 insert할꺼임

    @JoinColumn(name = "userId")
    @ManyToOne
    private User user; // object는 foriegn key 로 저장됨

    //이미지 좋아요

    //댓글글

   private LocalDateTime createDate;

    //자동으로 들어가게 할꺼여서

    @PrePersist // 디비에 INSERT 되기 직전에 실행됨
    public void createDate(){
        this.createDate=LocalDateTime.now();
    }

}
