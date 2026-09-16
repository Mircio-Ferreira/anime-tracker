package org.cesar.demo.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.cesar.demo.backend.dto.anime.AnimeRequest;
import org.cesar.demo.backend.dto.anime.AnimeResponse;
import org.cesar.demo.backend.entity.Anime;
import org.cesar.demo.backend.enums.Seasonal;
import org.cesar.demo.backend.exception.ConflictException;
import org.cesar.demo.backend.exception.NotFoundException;
import org.cesar.demo.backend.service.AnimeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Year;

@RestController
@RequestMapping("/animes")
@Tag(name = "Animes", description = "Anime management (title, schedule, episodes and season)")

public class AnimeController {

    private final AnimeService animeService;

    public AnimeController(AnimeService animeService){
        this.animeService = animeService;
    }

    @Operation(summary = "Find an anime", description = "Returns the anime for the given title, or 404 if it doesn't exist")
    @GetMapping("/{title}")
    public ResponseEntity<AnimeResponse> findAnime(@PathVariable String title){
        try{
            Anime anime = animeService.findAnime(title);
            return ResponseEntity.ok(AnimeResponse.fromEntity(anime));
        }catch (NotFoundException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,exception.getMessage());
        }
    }

    @Operation(summary = "List all animes", description = "Returns every anime currently stored, paginated")
    @GetMapping
    public ResponseEntity<Page<AnimeResponse>> findAll(@PageableDefault(size = 20) Pageable pageable){
        Page<AnimeResponse> animes = animeService.findAll(pageable).map(AnimeResponse::fromEntity);
        return ResponseEntity.ok(animes);
    }

    @Operation(summary = "List animes by season", description = "Returns every anime for the given year/seasonal, paginated, or 404 if that season doesn't exist")
    @GetMapping("/season/{seasonYear}/{seasonal}")
    public ResponseEntity<Page<AnimeResponse>> findAnimesBySeason(
            @PathVariable Year seasonYear,
            @PathVariable Seasonal seasonal,
            @PageableDefault(size = 20) Pageable pageable){
        try{
            Page<AnimeResponse> animes = animeService.findAnimesBySeason(seasonYear, seasonal, pageable)
                    .map(AnimeResponse::fromEntity);
            return ResponseEntity.ok(animes);
        }catch (NotFoundException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @Operation(summary = "Create an anime", description = "Creates a new anime, or 409 if the title already exists, or 404 if the given season doesn't exist")
    @PostMapping
    public ResponseEntity<AnimeResponse> createAnime(@RequestBody @Valid AnimeRequest request){
        try{
            Anime newAnime = animeService.createAnime(
                    request.title(),
                    request.dayOfWeek(),
                    request.totalEpisodes(),
                    request.studio(),
                    request.imageUrl(),
                    request.seasonYear(),
                    request.seasonal());
            return ResponseEntity.status(HttpStatus.CREATED).body(AnimeResponse.fromEntity(newAnime));
        }catch (ConflictException exception){
            throw new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage());
        }catch (NotFoundException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @Operation(summary = "Update an anime", description = "Updates the data of an existing anime, or 404 if the anime/season doesn't exist, or 409 if the new title is already taken")
    @PutMapping("/{currentTitle}")
    public ResponseEntity<AnimeResponse> updateAnime(
            @PathVariable String currentTitle,
            @RequestBody @Valid AnimeRequest request){
        try{
            Anime updated = animeService.updateAnime(
                    currentTitle,
                    request.title(),
                    request.dayOfWeek(),
                    request.totalEpisodes(),
                    request.studio(),
                    request.imageUrl(),
                    request.seasonYear(),
                    request.seasonal());
            return ResponseEntity.ok(AnimeResponse.fromEntity(updated));
        }catch (NotFoundException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }catch (ConflictException exception){
            throw new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage());
        }
    }

    @Operation(summary = "Delete an anime", description = "Deletes the anime for the given title, or 404 if it doesn't exist")
    @DeleteMapping("/{title}")
    public ResponseEntity<Void> deleteAnime(@PathVariable String title){
        try{
            animeService.deleteAnime(title);
            return ResponseEntity.noContent().build();
        }catch (NotFoundException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }


}
