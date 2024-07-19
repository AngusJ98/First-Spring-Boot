package com.example.sakilademo.actors;

import com.example.sakilademo.films.Film;
import com.example.sakilademo.films.FilmInput;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
class ActorServiceTest {

    private ActorRepository mockRepo;
    private ActorService actorService;

    @BeforeEach
    void setUp() {
        mockRepo = mock(ActorRepository.class);
        actorService = new ActorService(mockRepo);
        doThrow(new EntityNotFoundException()).when(mockRepo).findById(anyShort());
        doReturn(new Actor((short) 1, "Fergus", "Bentley", new ArrayList<Film>())).when(mockRepo).findById((short) 1);
        doReturn(new Actor((short) a, "Fergus", "Bentley", new ArrayList<Film>())).when(mockRepo).save())
        actorService = new ActorService(mockRepo);
    }

    @Test
    void testCreateActor() {
        ActorResponse result = actorService.createActor(new ActorInput("Fred", "Bill"));
        Assertions.assertEquals(new ActorResponse(new Actor((short) 0, "Fred", "Bill", List.of(new Film(new FilmInput())))), result);
    }
}
