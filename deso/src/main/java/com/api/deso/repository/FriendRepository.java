package com.api.deso.repository;

import com.api.deso.model.entity.Friend;
import com.api.deso.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {


    Friend findByUserId2(Long userid);

    List<Friend> findByUserId(Long id);

    List<Friend> findByUserIdAndUserId2(Long id1, Long id2);

    void deleteAllByUserId(Long id);

}
