package org.cesar.demo.backend.repository;

import org.cesar.demo.backend.entity.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimeRepository extends JpaRepository <Anime,Long> {
}
