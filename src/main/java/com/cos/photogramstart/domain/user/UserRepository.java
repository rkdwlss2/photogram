package com.cos.photogramstart.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

//어노테이션이 없어도 IOC등록이 자동으로 됨 JPA레파지토리를 상속 했으면 ㅇㅇ
public interface UserRepository extends JpaRepository<User,Integer> {

}
