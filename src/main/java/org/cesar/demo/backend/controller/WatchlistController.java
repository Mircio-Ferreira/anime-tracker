package org.cesar.demo.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.cesar.demo.backend.dto.watchlist.WatchlistRequest;
import org.cesar.demo.backend.dto.watchlist.WatchlistResponse;
import org.cesar.demo.backend.entity.Watchlist;
import org.cesar.demo.backend.enums.Seasonal;
import org.cesar.demo.backend.service.WatchlistService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Year;

@RestController
@RequestMapping("/watchlist")
@Tag(name = "Watchlist", description = "Tracks which animes a user is watching and their progress")

public class WatchlistController {

    private final WatchlistService watchlistService;

    public WatchlistController(WatchlistService watchlistService){
        this.watchlistService = watchlistService;
    }

    @Operation(summary = "Add an anime to the watchlist", description = "Creates a new watchlist entry, or 409 if it already exists, or 404 if the user/anime doesn't exist")
    @PostMapping
    public ResponseEntity<WatchlistResponse> addToWatchlist(@RequestBody @Valid WatchlistRequest request){
        Watchlist newWatchlist = watchlistService.addToWatchlist(
                request.userLogin(),
                request.animeTitle(),
                request.watchingStatus(),
                request.episodesWatched(),
                request.notes());
        return ResponseEntity.status(HttpStatus.CREATED).body(WatchlistResponse.fromEntity(newWatchlist));
    }

    @Operation(summary = "Find a watchlist entry", description = "Returns the watchlist entry for the given user/anime, or 404 if it doesn't exist")
    @GetMapping("/{userLogin}/{animeTitle}")
    public ResponseEntity<WatchlistResponse> findWatchlist(@PathVariable String userLogin, @PathVariable String animeTitle){
        Watchlist watchlist = watchlistService.findWatchlist(userLogin, animeTitle);
        return ResponseEntity.ok(WatchlistResponse.fromEntity(watchlist));
    }

    @Operation(summary = "List a user's watchlist", description = "Returns every watchlist entry for the given user, paginated")
    @GetMapping("/{userLogin}")
    public ResponseEntity<Page<WatchlistResponse>> findAllByUser(
            @PathVariable String userLogin,
            @PageableDefault(size = 20) Pageable pageable){
        Page<WatchlistResponse> watchlist = watchlistService.findAllByUser(userLogin, pageable)
                .map(WatchlistResponse::fromEntity);
        return ResponseEntity.ok(watchlist);
    }

    @Operation(summary = "List a user's watchlist by season", description = "Returns the user's watchlist entries filtered by anime season, paginated, or 404 if the user/season doesn't exist")
    @GetMapping("/{userLogin}/season/{seasonYear}/{seasonal}")
    public ResponseEntity<Page<WatchlistResponse>> findByUserAndSeason(
            @PathVariable String userLogin,
            @PathVariable Year seasonYear,
            @PathVariable Seasonal seasonal,
            @PageableDefault(size = 20) Pageable pageable){
        Page<WatchlistResponse> watchlist = watchlistService.findByUserAndSeason(userLogin, seasonYear, seasonal, pageable)
                .map(WatchlistResponse::fromEntity);
        return ResponseEntity.ok(watchlist);
    }

    @Operation(summary = "Update watchlist progress", description = "Updates the status, episodes watched and notes of an existing watchlist entry, or 404 if it doesn't exist")
    @PutMapping("/{userLogin}/{animeTitle}")
    public ResponseEntity<WatchlistResponse> updateProgress(
            @PathVariable String userLogin,
            @PathVariable String animeTitle,
            @RequestBody @Valid WatchlistRequest request){
        Watchlist updated = watchlistService.updateProgress(
                userLogin,
                animeTitle,
                request.watchingStatus(),
                request.episodesWatched(),
                request.notes());
        return ResponseEntity.ok(WatchlistResponse.fromEntity(updated));
    }

    @Operation(summary = "Remove an anime from the watchlist", description = "Deletes the watchlist entry for the given user/anime, or 404 if it doesn't exist")
    @DeleteMapping("/{userLogin}/{animeTitle}")
    public ResponseEntity<Void> removeFromWatchlist(@PathVariable String userLogin, @PathVariable String animeTitle){
        watchlistService.removeFromWatchlist(userLogin, animeTitle);
        return ResponseEntity.noContent().build();
    }
}
