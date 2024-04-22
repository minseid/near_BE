package com.api.deso.repository;

import com.api.deso.dto.EventDtoClass;
import com.api.deso.model.entity.Event;
import com.api.deso.model.entity.EventHashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface EventHashtagRepository extends JpaRepository<EventHashtag, Long> {

    List<EventHashtag> findByEventId(Long id);

    @Query("select distinct e.event from EventHashtag e where e.content like %:content%")
    List<Event> findByContentLike(@Param("content") String content);

}
