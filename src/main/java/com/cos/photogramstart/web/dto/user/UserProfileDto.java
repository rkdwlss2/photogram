package com.cos.photogramstart.web.dto.user;

import com.cos.photogramstart.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileDto {
    private boolean pageOwnerState; //안만들고 해결할수 있지만 is 붙으면 파싱 안됨
    private int imageCount;
    private boolean subscribeState;
    private int subscribeCount;
    private User user;
}
