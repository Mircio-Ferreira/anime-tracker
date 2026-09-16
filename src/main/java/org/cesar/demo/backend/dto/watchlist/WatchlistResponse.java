package org.cesar.demo.backend.dto.watchlist;

import org.cesar.demo.backend.entity.Watchlist;
import org.cesar.demo.backend.enums.WatchingStatus;

public record WatchlistResponse(
        String userLogin,
        String animeTitle,
        WatchingStatus watchingStatus,
        int episodesWatched,
        String notes) {

    public static WatchlistResponse fromEntity(Watchlist watchlist){
        return new WatchlistResponse(
                watchlist.getUser().getUserLogin(),
                watchlist.getAnime().getTitle(),
                watchlist.getWatchingStatus(),
                watchlist.getEpisodesWatched(),
                watchlist.getNotes()
        );
    }
}
