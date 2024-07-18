package com.example.sakilademo.actors;

import com.example.sakilademo.films.PartialFilmResponse;
import lombok.Getter;

import java.util.List;

@Getter
public class PartialActorResponse {
    private final String firstName;
    private final String lastName;

    public PartialActorResponse(Actor actor) {
        this.firstName = actor.getFirstName();
        this.lastName = actor.getLastName();
    }
}
