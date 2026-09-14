package org.cesar.demo.backend.repository;

import org.cesar.demo.backend.entity.Watchlist;
import org.cesar.demo.backend.entity.WatchlistId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchlistRepository extends JpaRepository <Watchlist, WatchlistId> {
}
