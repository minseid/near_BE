package com.api.deso.repository;

import com.api.deso.model.entity.EventReviewImage;
import com.api.deso.model.entity.NoticeImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeImageRepository extends JpaRepository<NoticeImage, Long > {

    List<NoticeImage> findByNoticeId(Long id);

}
