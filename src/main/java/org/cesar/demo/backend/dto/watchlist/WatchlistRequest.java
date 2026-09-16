package org.cesar.demo.backend.dto.watchlist;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.cesar.demo.backend.enums.WatchingStatus;

public record WatchlistRequest(
        @NotBlank String userLogin,
        @NotBlank String animeTitle,
        @NotNull WatchingStatus watchingStatus,
        @PositiveOrZero Integer episodesWatched,
        String notes) {
}
