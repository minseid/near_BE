package com.api.deso.repository;

import com.api.deso.model.entity.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLogRepository extends JpaRepository<UserLog, Long> {

    UserLog findByUserId(Long userId);

}
