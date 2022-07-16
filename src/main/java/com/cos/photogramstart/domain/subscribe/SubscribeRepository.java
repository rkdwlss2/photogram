package com.cos.photogramstart.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe,Integer> {

    @Modifying // Insert , delete, update 를 네이티브 쿼리로 작성하려면 해당 어노테이션이 필요
    @Query(value = "INSERT INTO subscribe(fromUserId,toUserId,createDate) VALUES (:fromUserId,:toUserId,now())",nativeQuery = true)
    void mSubScribe(int fromUserId,int toUserId); //성공하면 1(변경된 행의 개수가 리턴됨), 실패하면 -1 리턴됨

    @Modifying
    @Query(value = "DELETE FROM subscribe WHERE fromUserId = :fromUserId AND toUserId = :toUserId",nativeQuery = true)
    void mUnSubscribe(int fromUserId,int toUserId); // 1,-1

    @Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId = :principalId AND toUserId = :pageUserId",nativeQuery = true) //select만 사용해서 modyfing 필요 없음음
    int mSubscribeState(int principalId, int pageUserId);

    @Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId = :pageUserId",nativeQuery = true)
    int mSubscribeCount(int pageUserId);

}
