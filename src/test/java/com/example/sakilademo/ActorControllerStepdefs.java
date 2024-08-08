package com.example.sakilademo;

import com.example.sakilademo.actors.ActorController;
import com.example.sakilademo.actors.ActorInput;
import com.example.sakilademo.actors.ActorResponse;
import com.example.sakilademo.actors.ActorService;
import com.example.sakilademo.films.PartialFilmResponse;
import io.cucumber.java.Before;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class ActorControllerStepdefs {
    private ActorService mockService;
    private ActorController actorController;
    private ActorResponse actorResponse;
    private ResponseEntity<ActorResponse> responseEntity;
    private int statusCode;
    private ActorInput actorInput;
    private ResponseEntity<HttpStatus> deleteResponseEntity;

    @Before
    public void setup() {
        mockService = mock(ActorService.class);
        actorController = new ActorController(mockService);
    }



    @Given("an actor exists with ID {short}")
    public void anActorExistsWithID(short actorId) {
        ActorResponse newActorResponse = new ActorResponse((short) actorId, "First", "Last", new ArrayList<>());
        when(mockService.getOneActor(actorId))
                .thenReturn(newActorResponse);
        when(mockService.updateActor(any(ActorInput.class),eq(actorId)))
                .thenReturn(new ActorResponse(actorId, "UpdatedFirst", "UpdatedLast", new ArrayList<>()));
    }

    @Given("a valid ActorInput request body")
    public void aValidActorInputRequestBody() {
        actorInput = new ActorInput("First", "Last");
        when(mockService.createActor(any(ActorInput.class)))
                .thenReturn(new ActorResponse((short)4, "First", "Last", new ArrayList<>()));

    }

    @Given("an actor does not exist with ID {short}")
    public void anActorDoesNotExistWithID(short actorId) {
        when(mockService.getOneActor(actorId)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(mockService).deleteActor(actorId);
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(mockService).updateActor(any(ActorInput.class), eq(actorId));


    }


    @When("a GET request is made to \\/actors\\/{int}")
    public void aGETRequestIsMadeToActors(int actorId) {
        responseEntity = actorController.getActorById((short) actorId);
    }




    @When("a POST request is made to the actors collection")
    public void aPOSTRequestIsMadeToTheActorsCollection() {
        responseEntity = actorController.newActor(actorInput);
    }


    @Then("an ActorResponse is returned")
    public void anActorResponseIsReturned() {
        Assert.assertNotNull(responseEntity.getBody().getFirstName());
    }

    @And("the status code is {int}")
    public void theStatusCodeIs(int expectedStatus) {
        int actualStatus = responseEntity.getStatusCode().value();
        Assert.assertEquals(expectedStatus, actualStatus);
    }





    @Then("an empty response is returned")
    public void anEmptyResponseIsReturned() {
        Assert.assertNull(responseEntity.getBody());
    }


    @Then("a blank response is returned")
    public void aBlankResponseIsReturned() {
        Assert.assertNull(responseEntity.getBody());
    }

    @When("a PATCH request is made to the collection with ID {short}")
    public void aPATCHRequestIsMadeToTheCollectionWithID(short actorId) {
        when(mockService.updateActor(any(ActorInput.class),eq(actorId)))
                .thenReturn(new ActorResponse(actorId, "UpdatedFirst", "UpdatedLast", new ArrayList<>()));
        responseEntity = actorController.patchActor(actorId, new ActorInput("UpdatedFirst", "UpdatedLast"));
    }

    @When("a DELETE request is made to the actors collection with ID {int}")
    public void aDELETERequestIsMadeToTheActorsCollectionWithID(int actorId) {
        responseEntity = actorController.deleteActor((short)actorId);
    }

    @And("the status code of the DELETE request is {int}")
    public void theStatusCodeOfTheDELETERequestIs(int expectedCode) {
        Assert.assertEquals(expectedCode, responseEntity.getStatusCode().value());
    }

    @When("a PUT request is made to the collection with ID {short}")
    public void aPUTRequestIsMadeToTheCollectionWithID(short actorId) {
        responseEntity = actorController.patchActor(actorId, new ActorInput("UpdatedFirst", "UpdatedLast"));
        responseEntity = actorController.updateActor(actorId, new ActorInput("UpdatedFirst", "UpdatedLast"));
    }

    @Given("an invalid ActorInput")
    public void anInvalidActorInput() {

    }
}
