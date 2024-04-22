package com.api.deso.repository;

import com.api.deso.model.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findAll(Pageable pageable);

    List<Event> findByTitleContaining(String name);

    List<Event> findByCategoryContaining(String category);

    @Modifying
    @Transactional
    @Query("update Event e set e.view=e.view+1 where e.id=:id")
    void increaseViewById(@Param("id") Long id);

    @Query(value = "select * from event order by RAND() limit :num",nativeQuery = true)
    List<Event> eventRandomSearch(@Param("num") Long num);


    List<Event> findAllByEndDateIsBetween(LocalDateTime now,LocalDateTime end);
}
