package com.api.deso.repository;

import com.api.deso.model.entity.Banner;
import com.api.deso.model.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> OrderByCreatedAt();
}
