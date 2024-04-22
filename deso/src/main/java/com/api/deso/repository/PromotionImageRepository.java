package com.api.deso.repository;

import com.api.deso.model.entity.EventImage;
import com.api.deso.model.entity.PromotionImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionImageRepository extends JpaRepository<PromotionImage, Long> {
    List<PromotionImage> findByEventId(Long id);
}
