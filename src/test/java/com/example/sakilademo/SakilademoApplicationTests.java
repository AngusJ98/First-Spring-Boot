package com.example.sakilademo;

import com.example.sakilademo.actors.Actor;
import com.example.sakilademo.actors.ActorController;
import com.example.sakilademo.actors.ActorResponse;
import com.example.sakilademo.actors.ActorService;
import com.example.sakilademo.films.Film;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class SakilademoApplicationTests {

	private ActorController actorController;

	@BeforeEach
	public void setup() {
		final var mockService = mock(ActorService.class);
		//when(mockService.getOneActor((short) 1)).thenReturn(ResponseEntity.ok(new ActorResponse(new Actor((short) 1, "Fergus", "Bentley", new ArrayList<Film>()))));

		doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(mockService).getOneActor(anyShort());
		doReturn(new ActorResponse(new Actor((short) 1, "Fergus", "Bentley", new ArrayList<Film>()))).when(mockService).getOneActor((short) 1);
		actorController = new ActorController(mockService);
	}

	@Test
	void twoPlusTwo() {
		final var expected = 4;
		final var actual = 2 + 2;
		Assertions.assertEquals(expected, actual, "2+2=4");
	}

	@Test
	public void actorControllerGetsExistingActor() {
		final short expectedId = 1;
		final var expectedFirstName = "Fergus";
		final var expectedLastName = "Bentley";

		final var actual = actorController.getActorById((short)1);
		Assertions.assertEquals(expectedId, actual.getId());
		Assertions.assertEquals(expectedFirstName, actual.getFirstName());
		Assertions.assertEquals(expectedLastName, actual.getLastName());
	}

	@Test public void setActorControllerFindActorThrows404WhenInvalid(){
		Exception exception = null;
		try {
			actorController.getActorById((short)100002);
		} catch (Exception e) {
			exception = e;
		}

		Assertions.assertNotNull(exception);
		Assertions.assertInstanceOf(ResponseStatusException.class, exception);
		Assertions.assertEquals(String.valueOf(HttpStatus.NOT_FOUND), String.valueOf(exception.getMessage()));
	}


}
