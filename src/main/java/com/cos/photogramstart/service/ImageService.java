package com.cos.photogramstart.service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Transactional(readOnly = true)
    public List<Image> 인기사진(){
        return imageRepository.mPopular();
    }

    @Transactional(readOnly = true) // 영속성 컨텍스트 변경 감지를 해서 더티체킹, flush(반영) x
    public Page<Image> 이미지스토리(int principalId, Pageable pageable){
        Page<Image> images = imageRepository.mStory(principalId,pageable);

        // images에 좋아요 상태 담기
        images.forEach((image) -> {

            image.setLikeCount(image.getLikes().size());

            image.getLikes().forEach((like) -> {
                if (like.getUser().getId() == principalId){ // 해당 이미지의 좋아요한 사람들을 찾아서 현재 로그인한 사람이 좋아요 한것인지 비교하는 것임
                    image.setLikeState(true);
                }
            });

        });
        return images;
    }

    @Value("${file.path}")
    private String uploadFolder;

    public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails){
        UUID uuid = UUID.randomUUID(); // uuid

        String imageFileName = uuid + "_"+imageUploadDto.getFile().getOriginalFilename(); // 파일명이 1.jpg 면 -> 1.jpg가 파일 네임으로 들어옴

        System.out.println("이미지 파일 이름: "+ imageFileName);

        Path imageFilePath = Paths.get(uploadFolder+imageFileName);  // 실제 경로를 넣어야 함

        // try catch 쓴 이유 통신, I/O가 일어날때 예외가 발생할 수 있음 이런것들은 컴파일 시에 못잡은 런타임에 일어남
        try {
            Files.write(imageFilePath,imageUploadDto.getFile().getBytes()); // 파일을 씀
        }catch (Exception e){
            e.printStackTrace();
        }

        // image 테이블에 저장
        Image image = imageUploadDto.toEntity(imageFileName,principalDetails.getUser()); // UUID + 파일이름.jpg
//        Image imageEntity = imageRepository.save(image);
        imageRepository.save(image);
//        System.out.println(imageEntity.toString());

    }
}
