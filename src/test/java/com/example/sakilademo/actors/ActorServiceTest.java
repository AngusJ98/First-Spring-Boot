package com.example.sakilademo.actors;

import com.example.sakilademo.films.Film;
import com.example.sakilademo.films.FilmInput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.*;

class ActorServiceTest {
    @Mock
    ActorRepository actorRepository;
    @InjectMocks
    ActorService actorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateActor() {
        ActorResponse result = actorService.createActor(new ActorInput());
        Assertions.assertEquals(new ActorResponse(new Actor((short) 0, "firstName", "lastName", List.of(new Film(new FilmInput())))), result);
    }

    @Test
    void testGetAllActors() {
        when(actorRepository.findAll()).thenReturn(List.of(new Actor()));

        List<ActorResponse> result = actorService.getAllActors();
        Assertions.assertEquals(List.of(new ActorResponse(new Actor((short) 0, "firstName", "lastName", List.of(new Film(new FilmInput()))))), result);
    }

    @Test
    void testGetOneActor() {
        when(actorRepository.findById(anyShort())).thenReturn(new Actor((short) 0, "firstName", "lastName", List.of(new Film(new FilmInput()))));

        ActorResponse result = actorService.getOneActor((short) 0);
        Assertions.assertEquals(new ActorResponse(new Actor((short) 0, "firstName", "lastName", List.of(new Film(new FilmInput())))), result);
    }

    @Test
    void testUpdateActor() {
        when(actorRepository.findById(anyShort())).thenReturn(new Actor((short) 0, "firstName", "lastName", List.of(new Film(new FilmInput()))));
        when(actorRepository.save(any(Actor.class))).thenReturn(new Actor());

        ActorResponse result = actorService.updateActor(new ActorInput(), (short) 0);
        Assertions.assertEquals(new ActorResponse(new Actor((short) 0, "firstName", "lastName", List.of(new Film(new FilmInput())))), result);
    }

    @Test
    void testDeleteActor() {
        when(actorRepository.findById(anyShort())).thenReturn(new Actor((short) 0, "firstName", "lastName", List.of(new Film(new FilmInput()))));

        ResponseEntity<HttpStatus> result = actorService.deleteActor((short) 0);
        verify(actorRepository).deleteById(any(short.class));
        Assertions.assertEquals(new ResponseEntity<HttpStatus>(HttpStatus.CONTINUE, null, 0), result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme