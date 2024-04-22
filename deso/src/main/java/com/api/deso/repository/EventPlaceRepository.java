package com.api.deso.repository;

import com.api.deso.model.entity.Event;
import com.api.deso.model.entity.EventPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventPlaceRepository extends JpaRepository<EventPlace, Long> {
    EventPlace findByEventId(Long id);

    List<EventPlace> findByLocationContaining(String location);
}
