package org.cesar.demo.backend.repository;

import org.cesar.demo.backend.entity.Anime;
import org.cesar.demo.backend.entity.Season;
import org.cesar.demo.backend.entity.User;
import org.cesar.demo.backend.entity.Watchlist;
import org.cesar.demo.backend.entity.WatchlistId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WatchlistRepository extends JpaRepository <Watchlist, WatchlistId> {

    Optional<Watchlist> findByUserAndAnime(User user, Anime anime);

    Page<Watchlist> findByUser(User user, Pageable pageable);

    Page<Watchlist> findByUserAndAnime_Season(User user, Season season, Pageable pageable);
}
