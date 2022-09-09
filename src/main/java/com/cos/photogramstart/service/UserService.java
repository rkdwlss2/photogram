package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final SubscribeRepository subscribeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${file.path}")
    private String uploadFolder;

    @Transactional
    public User 회원프로필사진변경(int principalId, MultipartFile profileImageFile) {
        UUID uuid = UUID.randomUUID(); // uuid

        String imageFileName = uuid + "_"+profileImageFile.getOriginalFilename(); // 파일명이 1.jpg 면 -> 1.jpg가 파일 네임으로 들어옴

        System.out.println("이미지 파일 이름: "+ imageFileName);

        Path imageFilePath = Paths.get(uploadFolder+imageFileName);  // 실제 경로를 넣어야 함

        // try catch 쓴 이유 통신, I/O가 일어날때 예외가 발생할 수 있음 이런것들은 컴파일 시에 못잡은 런타임에 일어남
        try {
            Files.write(imageFilePath,profileImageFile.getBytes()); // 파일을 씀
        }catch (Exception e){
            e.printStackTrace();
        }

        User userEntity = userRepository.findById(principalId).orElseThrow(()->{
            throw new CustomApiException("유저를 찾을 수 없습니다.");
        });
        userEntity.setProfileImageUrl(imageFileName);

        return userEntity;
    } // 더티 체킹으로 업데이트 됨


    @Transactional(readOnly = true) // 영속성 컨텍스트 일을 적게 하는 방법임 변경 감지 계속 안함  더티 체킹
    public UserProfileDto 회원프로필(int pageUserId,int principalId){
        UserProfileDto dto = new UserProfileDto();


        // select * from image where userId = :userId; 쿼리로 하면 이렇게 하면됨
        User userEntity = userRepository.findById(pageUserId).orElseThrow(()->{
            throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
        });
//        System.out.println("====================");
//        userEntity.getImages().get(0);
        dto.setUser(userEntity);
        dto.setPageOwnerState(pageUserId == principalId); //1은 페이지 주인 ,-1은 주인이 아님
        dto.setImageCount(userEntity.getImages().size());

        int subscribeState = subscribeRepository.mSubscribeState(principalId,pageUserId);
        int subscribeCount = subscribeRepository.mSubscribeCount(pageUserId);

        dto.setSubscribeState(subscribeState==1);
        dto.setSubscribeCount(subscribeCount);

        userEntity.getImages().forEach((image -> {
            image.setLikeCount(image.getLikes().size());
        }));

        return dto;

    }

    @Transactional
    public User 회원수정(int id,User user){
        // 1. 영속화
//        User userEntity = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
//            @Override
//            public IllegalArgumentException get() {
//                return new IllegalArgumentException("찾을 수 없는 ID입니다.");
//            }
//        }); // 1. 무조건 찾았다 걱정마 get() 2. 못찾았어 exception 발동 시킬께 orElseThrow()

        User userEntity = userRepository.findById(id).orElseThrow(()->{return new CustomValidationApiException("찾을수 없는 Id입니다.");});



        // 2. 영속화된 오브젝트를 수정 - 더티체킹 (업테이트 완료)
        userEntity.setName(user.getName());

        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        userEntity.setPassword(encPassword);
        userEntity.setBio(user.getBio());
        userEntity.setWebsite(user.getWebsite());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());

        return userEntity;
    } //더티 체킹이 일어나서 업데이트가 완료됨


}
