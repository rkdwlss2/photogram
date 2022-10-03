package com.cos.photogramstart.domain.image;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.likes.Likes;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    @JsonIgnoreProperties({"images"})
    @JoinColumn(name = "userId")
    @ManyToOne
    private User user; // object는 foriegn key 로 저장됨

    //이미지 좋아요
    @JsonIgnoreProperties({"image"})
    @OneToMany(mappedBy = "image")
    private List<Likes> likes;

    // 댓글
    @OrderBy("id DESC")
    @JsonIgnoreProperties({"image"})
    @OneToMany(mappedBy = "image")
    private List<Comment> comments;

    //댓글글

   private LocalDateTime createDate;

   @Transient // DB에 컬럼이 만들어지지 않는다.
   private boolean likeState;
    //자동으로 들어가게 할꺼여서

    @Transient
    private int likeCount;

    @PrePersist // 디비에 INSERT 되기 직전에 실행됨
    public void createDate(){
        this.createDate=LocalDateTime.now();
    }


    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", caption='" + caption + '\'' +
                ", postImageUrl='" + postImageUrl + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
