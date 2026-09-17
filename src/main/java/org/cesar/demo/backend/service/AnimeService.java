package org.cesar.demo.backend.service;

import org.cesar.demo.backend.entity.Anime;
import org.cesar.demo.backend.entity.Season;
import org.cesar.demo.backend.enums.DayOfWeek;
import org.cesar.demo.backend.enums.Seasonal;
import org.cesar.demo.backend.exception.ConflictException;
import org.cesar.demo.backend.exception.NotFoundException;
import org.cesar.demo.backend.repository.AnimeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Year;

@Service
public class AnimeService {

    private final AnimeRepository animeRepository;
    private final SeasonService seasonService;

    public AnimeService(AnimeRepository animeRepository, SeasonService seasonService){
        this.animeRepository = animeRepository;
        this.seasonService = seasonService;
    }

    public Anime createAnime(String title, DayOfWeek dayOfWeek, Integer totalEpisodes, String studio, String imageUrl, Year seasonYear, Seasonal seasonal){
        if(animeRepository.findByTitle(title).isPresent()){
            throw new ConflictException("The anime: "+title+" already exist in system");
        }
        Season season = seasonService.findSeason(seasonYear, seasonal);
        Anime newAnime = new Anime(title,dayOfWeek,totalEpisodes,studio,imageUrl,season);
        animeRepository.save(newAnime);
        return  newAnime;
    }

    public Anime findAnime(String title){
        return animeRepository.findByTitle(title).orElseThrow(
                () -> new NotFoundException("The anime: "+ title + " don't exist")
        );
    }

    public Anime findAnimeById(Long id){
        return animeRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Anime not found: "+ id)
        );
    }

    public void deleteAnime(Long id){
       Anime deleteAnime = findAnimeById(id);
       animeRepository.delete(deleteAnime);
    }

    public Page<Anime> findAll(Pageable pageable){
        return animeRepository.findAll(pageable);
    }

    public Page<Anime> searchByTitle(String title, Pageable pageable){
        return animeRepository.findByTitleContainingIgnoreCase(title, pageable);
    }

    public Page<Anime> findAnimesBySeason(Year seasonYear, Seasonal seasonal, Pageable pageable){
        Season season = seasonService.findSeason(seasonYear, seasonal);
        return animeRepository.findBySeason(season, pageable);
    }

    public Anime updateAnime(Long id, String newTitle, DayOfWeek dayOfWeek, Integer totalEpisodes, String studio, String imageUrl, Year seasonYear, Seasonal seasonal){

        Anime currentAnime = findAnimeById(id);

        animeRepository.findByTitle(newTitle).ifPresent(existingAnime -> {
            if(!existingAnime.getId().equals(id)){
                throw new ConflictException("The anime: "+newTitle+" already exist in system");
            }
        });

        Season season = seasonService.findSeason(seasonYear,seasonal);

        currentAnime.setTitle(newTitle);
        currentAnime.setDayOfWeek(dayOfWeek);
        currentAnime.setTotalEpisodes(totalEpisodes);
        currentAnime.setStudio(studio);
        currentAnime.setImageUrl(imageUrl);
        currentAnime.setSeason(season);

        return animeRepository.save(currentAnime);
    }
}
