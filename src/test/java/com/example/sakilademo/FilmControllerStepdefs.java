package com.example.sakilademo;

import com.example.sakilademo.actors.PartialActorResponse;
import com.example.sakilademo.films.*;
import com.example.sakilademo.language.Language;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FilmControllerStepdefs {
    private FilmService mockService;
    private FilmController filmController;
    private FilmResponse filmResponse;
    private ResponseEntity<FilmResponse> responseEntity;
    private int statusCode;
    private FilmInput filmInput;

    @Before
    public void setup() {
        mockService = mock(FilmService.class);
        filmController = new FilmController(mockService);
    }



    @Given("an film exists with ID {short}")
    public void anFilmExistsWithID(short filmId) {
        FilmResponse newFilmResponse = new FilmResponse(filmId, "A Test Film", Year.of(2024), new Language(), new ArrayList<>(), new Language(), "Description", (short) 24,BigDecimal.valueOf(24), (short) 24,BigDecimal.valueOf(24), Rating.PG_13,new ArrayList<>(), LocalDateTime.now());
        when(mockService.getFilmById(filmId))
                .thenReturn(newFilmResponse);
    }

    @When("a GET request is made to \\/films\\/{int}")
    public void aGETRequestIsMadeToFilms(int filmId) {
        responseEntity = filmController.getFilmById((short) filmId);
    }

    @Then("an FilmResponse is returned")
    public void anFilmResponseIsReturned() {
        Assert.assertNotNull(responseEntity.getBody());
    }

    @And("the status code for the film is {int}")
    public void theStatusCodeIs(int expectedStatus) {
        int actualStatus = responseEntity.getStatusCode().value();
        Assert.assertEquals(expectedStatus, actualStatus);
    }

    @Given("a valid FilmInput request body")
    public void aValidFilmInputRequestBody() {
        filmInput = new FilmInput("A Test Film", "description", Year.of(2011), (short) 1, (short) 1, new Language(), (short) 24,BigDecimal.valueOf(24), (short) 24, BigDecimal.valueOf(24),Rating.PG_13, new ArrayList<>(), new ArrayList<>());
        when(mockService.createFilm(any(FilmInput.class)))
                .thenReturn(new FilmResponse((short) 4, "A Test Film", Year.of(2024), new Language(), new ArrayList<>(), new Language(), "Description", (short) 24,BigDecimal.valueOf(24), (short) 24,BigDecimal.valueOf(24), Rating.PG_13,new ArrayList<>(), LocalDateTime.now()));

    }

    @When("a POST request is made to the films collection")
    public void aPOSTRequestIsMadeToTheFilmsCollection() {
        responseEntity = filmController.createFilm(filmInput);
    }


    @Given("an film does not exist with ID {int}")
    public void anFilmDoesNotExistWithID(int filmId) {
        when(mockService.getFilmById((short) filmId)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Then("an empty film response is returned")
    public void anEmptyResponseIsReturned() {
        Assert.assertNull(responseEntity.getBody());
    }
}
