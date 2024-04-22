package com.api.deso.repository;

import com.api.deso.model.entity.EventReview;
import com.api.deso.model.entity.EventReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventReviewImageRepository extends JpaRepository<EventReviewImage, Long > {

    List<EventReviewImage> findByEventReviewId(Long id);

}
