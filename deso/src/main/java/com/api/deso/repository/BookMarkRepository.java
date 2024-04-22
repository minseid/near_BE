package com.api.deso.repository;

import com.api.deso.model.entity.BookMark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {

    List<BookMark> findByEventId(Long id);
    List<BookMark> findByUserId(Long id);

    void deleteAllByUserId(Long userId);

}
