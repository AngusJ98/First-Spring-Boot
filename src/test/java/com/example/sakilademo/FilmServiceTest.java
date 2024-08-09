package com.example.sakilademo;

import com.example.sakilademo.actors.ActorRepository;
import com.example.sakilademo.films.*;
import com.example.sakilademo.films.Film;
import com.example.sakilademo.language.Language;
import com.example.sakilademo.language.LanguageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyShort;
import static org.mockito.Mockito.*;

class FilmServiceTest {
    private FilmService filmService;
    private FilmRepository mockFilmRepo;
    private LanguageRepository mockLanguageRepo;
    private ActorRepository mockActorRepo;
    @BeforeEach
    public void setup() {
        mockFilmRepo = mock(FilmRepository.class);
        mockLanguageRepo = mock(LanguageRepository.class);
        mockActorRepo = mock(ActorRepository.class);
        Film example = new Film((short) 1, "test1!", "A Stunning Reflection of a Robot And a Moose who must Challenge a Woman in California", Year.of(2024), new Language(), new Language(), (short) 6, BigDecimal.valueOf(1), (short) 2, BigDecimal.valueOf(77), Rating.PG_13, List.of(SpecialFeature.BEHIND_THE_SCENES), LocalDateTime.now(),  List.of());
        Film example2 = new Film((short) 2, "test2!", "A Stunning Reflection of a Robot And a Moose who must Challenge a Woman in California", Year.of(2024), new Language(), new Language(), (short) 6, BigDecimal.valueOf(1), (short) 2, BigDecimal.valueOf(77), Rating.PG_13, List.of(SpecialFeature.BEHIND_THE_SCENES), LocalDateTime.now(),  List.of());
        doThrow(new EntityNotFoundException()).when(mockFilmRepo).findById(anyShort());
        doReturn(example).when(mockFilmRepo).findById((short) 1);
        doReturn(Optional.of(new Language())).when(mockLanguageRepo).findById((short) 1);
        filmService = new FilmService(mockFilmRepo, mockLanguageRepo, mockActorRepo);
        when(mockFilmRepo.save(any())).thenAnswer(i -> i.getArguments()[0]);
        when(mockFilmRepo.findAll()).thenReturn(Arrays.asList(example, example2));
        doThrow(new EntityNotFoundException()).when(mockFilmRepo).deleteById((short) 5100);
    }

    @Test
    void getFilmById() {
        FilmResponse res = filmService.getFilmById((short)1);
        Assertions.assertNotNull(res);
        Assertions.assertInstanceOf(FilmResponse.class, res);
    }

    @Test
    void getNonexistentFilmById() {
        Assertions.assertThrows(ResponseStatusException.class,
                () -> filmService.getFilmById((short) 50000));
    }


    @Test
    void getAllFilms() {
        List<FilmResponse> allFilms = filmService.getAllFilms();
        Assertions.assertEquals(2, allFilms.size());
    }

    @Test
    void deleteFilm() {
        Assertions.assertDoesNotThrow(() -> filmService.deleteFilm((short) 42));
    }

    @Test
    void deleteNonexistentFilm() {
        Assertions.assertThrows(ResponseStatusException.class,
                () -> filmService.deleteFilm((short) 5100));
    }

    @Test
    void updateFilm() {

        FilmInput filmData = new FilmInput();
        filmData.setTitle("Updated Title");
        filmData.setDescription("Updated Description");

        FilmResponse response = filmService.updateFilm((short) 1, filmData);

        Assertions.assertNotNull(response);
        Assertions.assertEquals("Updated Title", response.getTitle());
        Assertions.assertEquals("Updated Description", response.getDescription());

        verify(mockFilmRepo, times(1)).save(any(Film.class));
    }

    @Test
    void updateNonexistentFilm() {
        FilmInput filmData = new FilmInput();
        filmData.setTitle("Updated Title");

        Assertions.assertThrows(ResponseStatusException.class,
                () -> filmService.updateFilm((short) 50000, filmData));

    }

    @Test
    void createFilm() {
        FilmInput filmData = new FilmInput();
        filmData.setTitle("New Film");
        filmData.setDescription("A description for a new film");
        filmData.setLanguageId((short) 1);

        FilmResponse response = filmService.createFilm(filmData);

        Assertions.assertNotNull(response);
        Assertions.assertEquals("New Film", response.getTitle());
        Assertions.assertEquals("A description for a new film", response.getDescription());

        verify(mockFilmRepo, times(1)).save(any(Film.class));
    }

    @Test
    void patchFilm() {

        FilmInput filmData = new FilmInput();
        filmData.setTitle("Patched Title");

        Film existingFilm = mockFilmRepo.findById((short) 1);

        FilmResponse response = filmService.patchFilm((short) 1, filmData);

        Assertions.assertNotNull(response);
        Assertions.assertEquals("Patched Title", response.getTitle());

        Assertions.assertEquals(existingFilm.getDescription(), response.getDescription());

        verify(mockFilmRepo, times(1)).save(any(Film.class));
    }
}