package org.cesar.demo.backend.service;


import org.cesar.demo.backend.entity.Anime;
import org.cesar.demo.backend.entity.Season;
import org.cesar.demo.backend.enums.DayOfWeek;
import org.cesar.demo.backend.enums.Seasonal;
import org.cesar.demo.backend.exception.ConflictException;
import org.cesar.demo.backend.exception.NotFoundException;
import org.cesar.demo.backend.repository.AnimeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;



import java.time.Year;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AnimeServiceTest {

    @Mock
    private AnimeRepository animeRepository;

    @Mock
    private SeasonService seasonService;

    @InjectMocks
    private AnimeService animeService;

    private Season seasonTest_1;
    private final Year yearTest_1 = Year.of(2026);
    private final Seasonal seasonalTest_1 = Seasonal.FALL;

    @BeforeEach
    void setUp(){
        seasonTest_1= new Season(yearTest_1,seasonalTest_1);
    }

    @Test
    void createAnime_whenTitleIsNew_AndReturnAnime(){

        //We can't create a anime if the title is exist !
        when(animeRepository.findByTitle("title1")).thenReturn(Optional.empty());
        when(seasonService.findSeason(yearTest_1,seasonalTest_1)).thenReturn(seasonTest_1);

        Anime result = animeService.createAnime("title1", DayOfWeek.MONDAY, 28, "studio", "img", yearTest_1, seasonalTest_1);

        assertThat(result.getTitle()).isEqualTo("title1");
        assertThat(result.getSeason()).isEqualTo(seasonTest_1);
        verify(animeRepository).save(result);
    }

    @Test
    void createAnime_whenTitleAlreadyExists_throwsConflictException(){

        when(animeRepository.findByTitle("title1")).thenReturn(Optional.of(new Anime()));

        assertThatThrownBy(() ->
                animeService.createAnime("title1", DayOfWeek.MONDAY, 28, "studio", "img", yearTest_1, seasonalTest_1)
        ).isInstanceOf(ConflictException.class);

        verify(animeRepository, never()).save(any());
    }


    @Test
    void createAnime_whenSeasonDoesNotExist_propagatesNotFoundException(){
        //The anime with title title1 don't exist
        when(animeRepository.findByTitle("title1")).thenReturn(Optional.empty());
        when(seasonService.findSeason(yearTest_1, seasonalTest_1)).thenThrow(new NotFoundException("Season not found"));

        assertThatThrownBy(() ->
                animeService.createAnime("title1", DayOfWeek.MONDAY, 28, "studio", "img", yearTest_1, seasonalTest_1)
        ).isInstanceOf(NotFoundException.class);

        verify(animeRepository, never()).save(any());
    }

    @Test
    void findAnime_whenTitleDoesNotExist_throwsNotFoundException(){
        when(animeRepository.findByTitle("title1")).thenReturn(Optional.empty());

        assertThatThrownBy( () ->
                animeService.findAnime("title1")).isInstanceOf(NotFoundException.class);
    }

    @Test
    void findAnimeById_whenNotFound_throwsNotFoundException(){
        when(animeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy( () ->
                animeService.findAnimeById(1L)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void deleteAnime_whenExists(){
        Anime anime = new Anime("title1", DayOfWeek.MONDAY, 28, "studio", "img", seasonTest_1);
        when(animeRepository.findById(1L)).thenReturn(Optional.of(anime));

        animeService.deleteAnime(1L);
        verify(animeRepository).delete(anime);
    }

    @Test
    void deleteAnime_whenNotExist_throwsNotFoundException(){
        when(animeRepository.findById(1L)).thenReturn(Optional.empty());

        Anime anime = new Anime("title1", DayOfWeek.MONDAY, 28, "studio", "img", seasonTest_1);
        assertThatThrownBy(() -> animeService.deleteAnime(1L))
                .isInstanceOf(NotFoundException.class);

        verify(animeRepository, never()).delete((any()));
    }

    @Test
    void updateAnime_whenNewTitleBelongsToAnotherAnime_throwsConflicExeption(){
        Anime currentAnime = mock(Anime.class);
        when(animeRepository.findById(1L)).thenReturn(Optional.of(currentAnime));

        Anime otherAnime = mock(Anime.class);
        when(otherAnime.getId()).thenReturn(2L);
        when(animeRepository.findByTitle("New Title")).thenReturn(Optional.of(otherAnime));

        assertThatThrownBy(() ->
                animeService.updateAnime(1L, "New Title", DayOfWeek.MONDAY, 28, "studio", "img", yearTest_1, seasonalTest_1)
        ).isInstanceOf(ConflictException.class);

        verify(animeRepository, never()).save(any());
    }

    @Test
    void updateAnime_whenTitleUnchanged_updatesFieldsAndSaves(){
        Anime currentAnime = mock(Anime.class);
        when(currentAnime.getId()).thenReturn(1L);
        when(animeRepository.findById(1L)).thenReturn(Optional.of(currentAnime));
        when(animeRepository.findByTitle("title1")).thenReturn(Optional.of(currentAnime));
        when(seasonService.findSeason(yearTest_1, seasonalTest_1)).thenReturn(seasonTest_1);
        when(animeRepository.save(currentAnime)).thenReturn(currentAnime);

        Anime result = animeService.updateAnime(1L, "title1", DayOfWeek.TUESDAY, 30, "New Studio", "img2", yearTest_1, seasonalTest_1);

        verify(currentAnime).setTitle("title1");
        verify(currentAnime).setDayOfWeek(DayOfWeek.TUESDAY);
        verify(currentAnime).setTotalEpisodes(30);
        verify(currentAnime).setStudio("New Studio");
        verify(currentAnime).setImageUrl("img2");
        verify(currentAnime).setSeason(seasonTest_1);
        assertThat(result).isEqualTo(currentAnime);
    }

    @Test
    void updateAnime_whenSeasonDoesNotExist_throwsNotFoundException(){
        Anime currentAnime = new Anime("title1", DayOfWeek.MONDAY, 28, "studio", "img", seasonTest_1);
        when(animeRepository.findById(1L)).thenReturn(Optional.of(currentAnime));
        when(animeRepository.findByTitle("title1")).thenReturn(Optional.empty());
        when(seasonService.findSeason(yearTest_1, seasonalTest_1)).thenThrow(new NotFoundException("Season not found"));

        assertThatThrownBy(() ->
                animeService.updateAnime(1L, "title1", DayOfWeek.MONDAY, 28, "studio", "img", yearTest_1, seasonalTest_1)
        ).isInstanceOf(NotFoundException.class);

        verify(animeRepository, never()).save(any());
    }
}


