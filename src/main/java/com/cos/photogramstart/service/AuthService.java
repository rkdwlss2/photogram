package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service // 1. IoC 2.트랜잭션 관리를 해줌
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional // 실행되고 종료될때 까지 transcation 걸림 Wrtie(insert, update, delete) 할때
    public User 회원가입(User user){ // user 외부에서 통신 받은 데이터를 user object에 답은것임
        //회원가입 진행
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword); //해쉬로 암호화 됨
        user.setPassword(encPassword); // 패스워드 암호화 된거 set
        user.setRole("ROLE_USER"); //회원가입 모두 USER로 관리자는 ROLE_ADMIN
        User userEntity = userRepository.save(user); // 데이터 return 받은거임
        return userEntity;
    }
}
