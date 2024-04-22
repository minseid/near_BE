package com.api.deso.repository;

import com.api.deso.model.entity.Event;
import com.api.deso.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAll(Pageable pageable);

    User findByEmail(String email);

    User findByPhoneNumber(String phone);

    User findByAuthKakao(String code);

    User findByAuthApple(String code);

}
