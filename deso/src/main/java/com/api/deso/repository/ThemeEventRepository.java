package com.api.deso.repository;

import com.api.deso.model.entity.Theme;
import com.api.deso.model.entity.ThemeEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThemeEventRepository extends JpaRepository<ThemeEvent, Long> {

    List<ThemeEvent> findByThemeId(Long id);
    List<ThemeEvent> findByEventId(Long id);



}
