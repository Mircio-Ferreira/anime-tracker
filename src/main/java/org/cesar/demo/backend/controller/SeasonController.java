package org.cesar.demo.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.cesar.demo.backend.entity.Season;
import org.cesar.demo.backend.enums.Seasonal;
import org.cesar.demo.backend.service.SeasonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.List;

@RestController
@RequestMapping("/seasons")
@Tag(name = "Seasons", description = "Season management (year + season)")

public class SeasonController {

    private final SeasonService service;

    public SeasonController(SeasonService service){
        this.service = service;
    }

    @Operation(summary = "Find a season", description = "Returns the season for the given year and seasonal, or 404 if it doesn't exist")
    @GetMapping("/{year}/{seasonal}")
    public ResponseEntity<Season> findSeason(@PathVariable Year year, @PathVariable Seasonal seasonal){
        return ResponseEntity.ok(service.findSeason(year,seasonal));
    }

    @Operation(summary = "List all seasons", description = "Returns every season currently stored")
    @GetMapping
    public ResponseEntity<List<Season>> findAllSeason(){
        return ResponseEntity.ok(service.findAllSeason());
    }

    @Operation(summary = "Create a season", description = "Creates a new season, or 409 if that year/seasonal combination already exists")
    @PostMapping
    public ResponseEntity<Season> createSeason(@RequestBody Season season){
        Season newSeason = service.createSeason(season.getYear(), season.getSeasonal());
        return ResponseEntity.status(HttpStatus.CREATED).body(newSeason);
    }

    @Operation(summary = "Delete a season", description = "Deletes the season for the given year and seasonal, or 404 if it doesn't exist")
    @DeleteMapping("/{year}/{seasonal}")
    public ResponseEntity<Void> deleteSeason(@PathVariable Year year,@PathVariable Seasonal seasonal){
        service.deleteSeason(year, seasonal);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update a season", description = "Updates the year/seasonal of an existing season, or 404/409 if it doesn't exist or the new combination is already taken")
    @PutMapping("/{currentYear}/{currentSeasonal}")
    public ResponseEntity<Season> updateSeason(
            @PathVariable Year currentYear,
            @PathVariable Seasonal currentSeasonal,
            @RequestBody Season season){
        Season updated = service.updateSeason(season.getYear(), currentYear, season.getSeasonal(), currentSeasonal);
        return ResponseEntity.ok(updated);
    }


}
