package org.cesar.demo.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.cesar.demo.backend.dto.anime.AnimeRequest;
import org.cesar.demo.backend.dto.anime.AnimeResponse;
import org.cesar.demo.backend.entity.Anime;
import org.cesar.demo.backend.enums.Seasonal;
import org.cesar.demo.backend.service.AnimeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Year;

@RestController
@RequestMapping("/animes")
@Tag(name = "Animes", description = "Anime management (title, schedule, episodes and season)")

public class AnimeController {

    private final AnimeService animeService;

    public AnimeController(AnimeService animeService){
        this.animeService = animeService;
    }

    @Operation(summary = "Find an anime", description = "Returns the anime for the given id, or 404 if it doesn't exist")
    @GetMapping("/{id}")
    public ResponseEntity<AnimeResponse> findAnime(@PathVariable Long id){
        Anime anime = animeService.findAnimeById(id);
        return ResponseEntity.ok(AnimeResponse.fromEntity(anime));
    }

    @Operation(summary = "List all animes", description = "Returns every anime currently stored, paginated")
    @GetMapping
    public ResponseEntity<Page<AnimeResponse>> findAll(@PageableDefault(size = 20) Pageable pageable){
        Page<AnimeResponse> animes = animeService.findAll(pageable).map(AnimeResponse::fromEntity);
        return ResponseEntity.ok(animes);
    }

    @Operation(summary = "Search animes by title", description = "Returns animes whose title contains the given text (case-insensitive), paginated")
    @GetMapping("/search")
    public ResponseEntity<Page<AnimeResponse>> searchByTitle(
            @RequestParam String title,
            @PageableDefault(size = 20) Pageable pageable){
        Page<AnimeResponse> animes = animeService.searchByTitle(title, pageable).map(AnimeResponse::fromEntity);
        return ResponseEntity.ok(animes);
    }

    @Operation(summary = "List animes by season", description = "Returns every anime for the given year/seasonal, paginated, or 404 if that season doesn't exist")
    @GetMapping("/season/{seasonYear}/{seasonal}")
    public ResponseEntity<Page<AnimeResponse>> findAnimesBySeason(
            @PathVariable Year seasonYear,
            @PathVariable Seasonal seasonal,
            @PageableDefault(size = 20) Pageable pageable){
        Page<AnimeResponse> animes = animeService.findAnimesBySeason(seasonYear, seasonal, pageable)
                .map(AnimeResponse::fromEntity);
        return ResponseEntity.ok(animes);
    }

    @Operation(summary = "Create an anime", description = "Creates a new anime, or 409 if the title already exists, or 404 if the given season doesn't exist")
    @PostMapping
    public ResponseEntity<AnimeResponse> createAnime(@RequestBody @Valid AnimeRequest request){
        Anime newAnime = animeService.createAnime(
                request.title(),
                request.dayOfWeek(),
                request.totalEpisodes(),
                request.studio(),
                request.imageUrl(),
                request.seasonYear(),
                request.seasonal());
        return ResponseEntity.status(HttpStatus.CREATED).body(AnimeResponse.fromEntity(newAnime));
    }

    @Operation(summary = "Update an anime", description = "Updates the data of an existing anime, or 404 if the anime/season doesn't exist, or 409 if the new title is already taken")
    @PutMapping("/{id}")
    public ResponseEntity<AnimeResponse> updateAnime(
            @PathVariable Long id,
            @RequestBody @Valid AnimeRequest request){
        Anime updated = animeService.updateAnime(
                id,
                request.title(),
                request.dayOfWeek(),
                request.totalEpisodes(),
                request.studio(),
                request.imageUrl(),
                request.seasonYear(),
                request.seasonal());
        return ResponseEntity.ok(AnimeResponse.fromEntity(updated));
    }

    @Operation(summary = "Delete an anime", description = "Deletes the anime for the given id, or 404 if it doesn't exist")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnime(@PathVariable Long id){
        animeService.deleteAnime(id);
        return ResponseEntity.noContent().build();
    }
}
