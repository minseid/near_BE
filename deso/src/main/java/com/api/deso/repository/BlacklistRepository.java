package com.api.deso.repository;

import com.api.deso.model.entity.Blacklist;
import com.api.deso.model.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlacklistRepository extends JpaRepository<Blacklist, Long> {

    Blacklist findByUserId2(Long userid);

    List<Blacklist> findByUserId(Long id);

    List<Blacklist> findByUserIdAndUserId2(Long id1, Long id2);
    void deleteAllByUserId(Long id);
}
