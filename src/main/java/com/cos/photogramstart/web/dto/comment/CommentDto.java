package com.cos.photogramstart.web.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

// NotNull = null 체크
// NotEmpty = null 체크 빈값 체크
// NotBlank = null , 빈값, 스페이스 까지

@Data
public class CommentDto {
    @NotBlank
    private String content;
    @NotNull // 빈값 체크
    private Integer imageId;
}
