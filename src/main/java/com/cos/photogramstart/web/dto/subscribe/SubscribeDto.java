package com.cos.photogramstart.web.dto.subscribe;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscribeDto {

    private int id; // toUserId 누구를 구독할지 그 id가 필요함
    private String username; //user name때문에 필요함
    private String profileImageUrl; // 사진때문에 필요함
    private Integer subscribeState; //구독 한 사람인지 아닌지
    private Integer equalUserState; //동일인인지 아닌지 체크 maria db는 integer로 받아야한다.

}
