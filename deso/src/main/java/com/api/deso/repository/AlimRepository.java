package com.api.deso.repository;

import com.api.deso.model.entity.Alim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlimRepository extends JpaRepository<Alim, Long> {
    Optional<List<Alim>> findByFromId(Long userId);
    Optional<List<Alim>> findByToId(Long userId);
    void deleteAllByToId(Long userId);
}
