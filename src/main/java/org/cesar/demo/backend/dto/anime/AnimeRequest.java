package org.cesar.demo.backend.dto.anime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.cesar.demo.backend.enums.DayOfWeek;
import org.cesar.demo.backend.enums.Seasonal;

import java.time.Year;

public record AnimeRequest(
        @NotBlank String title,
        @NotNull DayOfWeek dayOfWeek,
        @NotNull @Positive Integer totalEpisodes,
        String studio,
        String imageUrl,
        @NotNull Year seasonYear,
        @NotNull Seasonal seasonal) {

}
