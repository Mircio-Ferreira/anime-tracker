package org.cesar.demo.backend.service;

import org.cesar.demo.backend.entity.Season;
import org.cesar.demo.backend.enums.Seasonal;
import org.cesar.demo.backend.exception.ConflictException;
import org.cesar.demo.backend.exception.NotFoundException;
import org.cesar.demo.backend.repository.SeasonRepository;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service

public class SeasonService {

    private final SeasonRepository repository;

    public SeasonService(SeasonRepository repository){
        this.repository=repository;
    }

    public Season createSeason(Year year, Seasonal seasonal){
        if(repository.findByYearAndSeasonal(year,seasonal).isPresent()){
            throw new ConflictException("This Season already exist to: "+ seasonal + " " + year);
        }
        Season newSeason = new Season(year,seasonal);
        repository.save(newSeason);
        return newSeason;
    }

    public Season findSeason(Year year, Seasonal seasonal){
        return  repository.findByYearAndSeasonal(year, seasonal)
                .orElseThrow( () -> new NotFoundException("Season not found: " + seasonal + " " + year));
    }

    public void deleteSeason(Year year, Seasonal seasonal){
        Season season = findSeason(year, seasonal);
        repository.delete(season);
    }

    public Season updateSeason(Year newYear, Year currentYear, Seasonal newSeasonal, Seasonal currentSeasonal) {
        Season season = findSeason(currentYear, currentSeasonal);

        boolean identityChanged = !currentYear.equals(newYear) || currentSeasonal != newSeasonal;

        if (identityChanged && repository.findByYearAndSeasonal(newYear, newSeasonal).isPresent()) {
            throw new ConflictException("This Season already exists: " + newSeasonal + " " + newYear);
        }

        season.setYear(newYear);
        season.setSeasonal(newSeasonal);

        return repository.save(season);
    }

    public List<Season> findAllSeason(){
        return repository.findAll();
    }
}
