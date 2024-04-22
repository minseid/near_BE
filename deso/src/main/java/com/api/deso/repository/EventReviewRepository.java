package com.api.deso.repository;

import com.api.deso.model.entity.EventReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventReviewRepository extends JpaRepository<EventReview, Long> {

    List<EventReview> findByEventId(Long id);

}
