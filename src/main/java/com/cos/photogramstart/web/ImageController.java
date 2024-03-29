package com.cos.photogramstart.web;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class ImageController {

    private final ImageService imageService;

    @GetMapping({"/","/image/story"})
    public String story(){
        return "image/story";
    }


    //api로 구현한다면 - 이유 - 요청을 브라우져에서 하는게 아닌 안드로이드나, ios에서 요청을 하게 되면 api로 만들어야함
    @GetMapping({"/image/popular"})
    public String popular(Model model){
        // api는 데이터를 리턴하는 서버!!
        List<Image> images = imageService.인기사진();
        model.addAttribute("images",images);

        return "image/popular";
    }

    @GetMapping({"/image/upload"})
    public String upload(){
        return "image/upload";
    }

    @PostMapping("/image")
    public String imageUpload(ImageUploadDto imageUploadDto, @AuthenticationPrincipal PrincipalDetails principalDetails){
        // 깍두기
        if (imageUploadDto.getFile().isEmpty()){
//            System.out.println("이미지 첨부되지 않았습니다.");
            throw new CustomValidationException("이미지가 첨부되지 않았습니다.",null);
        }

        // 서비스 호출
        imageService.사진업로드(imageUploadDto,principalDetails);
        return "redirect:/user/"+principalDetails.getUser().getId();
    }
}
