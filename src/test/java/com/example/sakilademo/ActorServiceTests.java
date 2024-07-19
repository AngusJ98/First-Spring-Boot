package com.example.sakilademo;

import com.example.sakilademo.actors.*;
import com.example.sakilademo.films.Film;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyShort;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ActorServiceTests {

    private ActorService actorService;

    @BeforeEach
    public void setup() {
        final var mockRepo = mock(ActorRepository.class);
        //when(mockService.getOneActor((short) 1)).thenReturn(ResponseEntity.ok(new ActorResponse(new Actor((short) 1, "Fergus", "Bentley", new ArrayList<Film>()))));

        doThrow(new EntityNotFoundException()).when(mockRepo).findById(anyShort());
        doReturn(new Actor((short) 1, "Fergus", "Bentley", new ArrayList<Film>())).when(mockRepo).findById((short) 1);
        actorService = new ActorService(mockRepo);
    }

    @Test
    public void actorServiceGetsExistingActor() {
        final short expectedId = 1;
        final var expectedFirstName = "Fergus";
        final var expectedLastName = "Bentley";

        final var actual = actorService.getOneActor((short) 1);
        Assertions.assertEquals(expectedId, actual.getId());
        Assertions.assertEquals(expectedFirstName, actual.getFirstName());
        Assertions.assertEquals(expectedLastName, actual.getLastName());
    }

    @Test
    public void actorServiceCantFind() {
        Exception exception = null;
        try {
            actorService.getOneActor((short)100002);
        } catch (Exception e) {
            exception = e;
        }

        Assertions.assertNotNull(exception);
        Assertions.assertInstanceOf(EntityNotFoundException.class, exception);
    }

}
