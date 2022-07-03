package com.cos.photogramstart.config.auth;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    //원래 로그인 요청을 하면 로그인 요청인지 아닌지 판단 하고 나면
    // 1. 패스워드는 알아서 체킹하니깐 신경쓸필요 없다.
    // 2. 리턴이 잘되면 자동으로 UserDetails 타입을 세션을 만든다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username);
        if (userEntity==null){
            return null;
        }
        else{
            return new PrincipalDetails(userEntity);
        }

    }
}
