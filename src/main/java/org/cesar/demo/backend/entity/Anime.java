package org.cesar.demo.backend.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.cesar.demo.backend.enums.DayOfWeek;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Anime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    private String title;
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;
    private Integer totalEpisodes;
    private String studio;
    private String imageUrl;
    private boolean active = true;
    @ManyToOne
    @JoinColumn(name = "season_id")
    private Season season;

    public Anime(String title,DayOfWeek dayOfWeek, Integer totalEpisodes, String studio,String imageUrl, Season season){
        this.title = title;
        this.dayOfWeek = dayOfWeek;
        this.totalEpisodes = totalEpisodes;
        this.studio = studio;
        this.imageUrl = imageUrl;
        this.season = season;
    }
}
