package com.api.deso.repository;

import com.api.deso.model.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {

    Auth findTop1ByPhoneOrderByCreatedAtDesc(String phone);

    Auth findTop1ByCheckCharacterOrderByCreatedAtDesc(String check);
}
