package com.api.deso.repository;

import com.api.deso.model.entity.EventPostReview;
import com.api.deso.model.entity.EventReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventPostReviewRepository extends JpaRepository<EventPostReview, Long> {

    List<EventPostReview> findByEventId(Long id);

}
