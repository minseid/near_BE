package com.api.deso.repository;

import com.api.deso.model.entity.Banner;
import com.api.deso.model.entity.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendRepository extends JpaRepository<Recommend, Long> {

    List<Recommend> findByEventId(Long id);

}
