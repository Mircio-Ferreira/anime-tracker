package org.cesar.demo.backend.dto.anime;

import org.cesar.demo.backend.entity.Anime;
import org.cesar.demo.backend.enums.DayOfWeek;
import org.cesar.demo.backend.enums.Seasonal;

import java.time.Year;

public record AnimeResponse(
        Long id,
        String title,
        DayOfWeek dayOfWeek,
        Integer totalEpisodes,
        String studio,
        String imageUrl,
        Year seasonYear,
        Seasonal seasonal) {

    public static AnimeResponse fromEntity(Anime anime){
        return new AnimeResponse(
                anime.getId(),
                anime.getTitle(),
                anime.getDayOfWeek(),
                anime.getTotalEpisodes(),
                anime.getStudio(),
                anime.getImageUrl(),
                anime.getSeason().getYear(),
                anime.getSeason().getSeasonal()
        );
    }
}
