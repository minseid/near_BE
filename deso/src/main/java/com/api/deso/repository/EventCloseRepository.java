package com.api.deso.repository;

import com.api.deso.model.entity.EventClose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventCloseRepository extends JpaRepository<EventClose, Long> {
    List<EventClose> findByEventId(Long id);
}
