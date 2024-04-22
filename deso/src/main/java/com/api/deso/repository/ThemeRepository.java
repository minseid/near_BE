package com.api.deso.repository;

import com.api.deso.model.entity.Alim;
import com.api.deso.model.entity.Event;
import com.api.deso.model.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {

    List<Theme> findByContentContains(String name);

    @Query(value = "select * from theme order by RAND() limit :num",nativeQuery = true)
    List<Theme> themeRandomSearch(@Param("num") Long num);


}
