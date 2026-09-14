package org.cesar.demo.backend.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.cesar.demo.backend.enums.WatchingStatus;

@Entity
@IdClass(WatchlistId.class)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Watchlist {
    @Id
    @ManyToOne
    private User user;
    @Id
    @ManyToOne
    private Anime anime;
    @Enumerated(EnumType.STRING)
    private WatchingStatus watchingStatus;
    private int episodesWatched = 0;
    private String notes;

    public Watchlist(User user, Anime anime, WatchingStatus watchingStatus,int episodesWatched){
        this.user = user;
        this.anime = anime;
        this.watchingStatus = watchingStatus;
        this.episodesWatched = episodesWatched;
    }

    public Watchlist(User user, Anime anime, WatchingStatus watchingStatus,int episodesWatched,String notes){
        this(user, anime, watchingStatus, episodesWatched);
        this.notes = notes;
    }


}
