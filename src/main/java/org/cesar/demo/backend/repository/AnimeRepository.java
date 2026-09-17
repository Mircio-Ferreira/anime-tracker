package org.cesar.demo.backend.repository;

import org.cesar.demo.backend.entity.Anime;
import org.cesar.demo.backend.entity.Season;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnimeRepository extends JpaRepository <Anime,Long> {

    Optional<Anime> findByTitle(String title);

    Page<Anime> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Anime> findBySeason(Season season, Pageable pageable);

}
