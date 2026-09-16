package org.cesar.demo.backend.service;

import org.cesar.demo.backend.entity.Anime;
import org.cesar.demo.backend.entity.Season;
import org.cesar.demo.backend.entity.User;
import org.cesar.demo.backend.entity.Watchlist;
import org.cesar.demo.backend.enums.Seasonal;
import org.cesar.demo.backend.enums.WatchingStatus;
import org.cesar.demo.backend.exception.ConflictException;
import org.cesar.demo.backend.exception.NotFoundException;
import org.cesar.demo.backend.repository.WatchlistRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Year;

@Service
public class WatchlistService {

    private final WatchlistRepository watchlistRepository;
    private final UserService userService;
    private final AnimeService animeService;
    private final SeasonService seasonService;

    public WatchlistService(WatchlistRepository watchlistRepository, UserService userService, AnimeService animeService, SeasonService seasonService){
        this.watchlistRepository = watchlistRepository;
        this.userService = userService;
        this.animeService = animeService;
        this.seasonService = seasonService;
    }

    public Watchlist addToWatchlist(String userLogin, String animeTitle, WatchingStatus watchingStatus, int episodesWatched, String notes){
        User user = userService.findUser(userLogin);
        Anime anime = animeService.findAnime(animeTitle);

        if(watchlistRepository.findByUserAndAnime(user, anime).isPresent()){
            throw new ConflictException("The anime: "+animeTitle+" is already in the watchlist of: "+userLogin);
        }

        Watchlist newWatchlist = new Watchlist(user, anime, watchingStatus, episodesWatched, notes);
        return watchlistRepository.save(newWatchlist);
    }

    public Watchlist findWatchlist(String userLogin, String animeTitle){
        User user = userService.findUser(userLogin);
        Anime anime = animeService.findAnime(animeTitle);

        return watchlistRepository.findByUserAndAnime(user, anime).orElseThrow(
                () -> new NotFoundException("The anime: "+animeTitle+" is not in the watchlist of: "+userLogin)
        );
    }

    public Watchlist updateProgress(String userLogin, String animeTitle, WatchingStatus watchingStatus, int episodesWatched, String notes){
        Watchlist currentWatchlist = findWatchlist(userLogin, animeTitle);

        currentWatchlist.setWatchingStatus(watchingStatus);
        currentWatchlist.setEpisodesWatched(episodesWatched);
        currentWatchlist.setNotes(notes);

        return watchlistRepository.save(currentWatchlist);
    }

    public void removeFromWatchlist(String userLogin, String animeTitle){
        Watchlist deleteWatchlist = findWatchlist(userLogin, animeTitle);
        watchlistRepository.delete(deleteWatchlist);
    }

    public Page<Watchlist> findAllByUser(String userLogin, Pageable pageable){
        User user = userService.findUser(userLogin);
        return watchlistRepository.findByUser(user, pageable);
    }

    public Page<Watchlist> findByUserAndSeason(String userLogin, Year seasonYear, Seasonal seasonal, Pageable pageable){
        User user = userService.findUser(userLogin);
        Season season = seasonService.findSeason(seasonYear, seasonal);
        return watchlistRepository.findByUserAndAnime_Season(user, season, pageable);
    }
}
