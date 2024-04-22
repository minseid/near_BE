package com.api.deso.repository;

import com.api.deso.model.entity.EventOpen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventOpenRepository extends JpaRepository<EventOpen, Long> {
    List<EventOpen> findByEventId(Long id);
}
