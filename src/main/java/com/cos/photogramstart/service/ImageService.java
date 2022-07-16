package com.cos.photogramstart.service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

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
