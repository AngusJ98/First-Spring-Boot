package com.example.sakilademo;

import com.example.sakilademo.actors.*;
import com.example.sakilademo.films.Film;
import jakarta.persistence.EntityNotFoundException;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyShort;
import static org.mockito.Mockito.*;

@SpringBootTest
class ActorServiceTests {

    private ActorService actorService;

    @BeforeEach
    public void setup() {
        final var mockRepo = mock(ActorRepository.class);
        Actor example = new Actor((short) 1, "Fergus", "Bentley", new ArrayList<Film>());
        Actor example2 = new Actor((short) 2, "Tom", "Byars", new ArrayList<Film>());
        doThrow(new EntityNotFoundException()).when(mockRepo).findById(anyShort());
        doReturn(example).when(mockRepo).findById((short) 1);
        actorService = new ActorService(mockRepo);
        when(mockRepo.save(any())).thenAnswer(i -> i.getArguments()[0]);
        when(mockRepo.findAll()).thenReturn(Arrays.asList(example, example2));
    }

    @Test
    void actorServiceGetsExistingActor() {
        final short expectedId = 1;
        final var expectedFirstName = "Fergus";
        final var expectedLastName = "Bentley";

        final var actual = actorService.getOneActor((short) 1);
        Assertions.assertEquals(expectedId, actual.getId());
        Assertions.assertEquals(expectedFirstName, actual.getFirstName());
        Assertions.assertEquals(expectedLastName, actual.getLastName());
    }

    @Test
    void actorServiceCantFind() {
        Assertions.assertThrows(ResponseStatusException.class,
                () -> actorService.getOneActor((short)50000));
    }

    @Test
    void actorServiceCreatesAnActor() {
        String firstName = "Arda";
        String lastName = "Ordu";

        ActorResponse response = actorService.createActor(new ActorInput(firstName, lastName));
        Assertions.assertNotNull(response);
        Assertions.assertEquals(firstName, response.getFirstName());
        Assertions.assertEquals(lastName, response.getLastName());
        Assertions.assertEquals(response.getFilms(), new ArrayList<>());
    }

    @Test
    void actorServiceGetsAllActors() {
        List<ActorResponse> actors = actorService.getAllActors();
        Assertions.assertNotNull(actors);
        Assertions.assertEquals(2, actors.size());
    }
}
