package com.example.sakilademo.actors;

import com.example.sakilademo.films.PartialFilmResponse;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@EqualsAndHashCode
public class ActorResponse {
    private final Short id;
    private final String firstName;
    private final String lastName;
    @EqualsAndHashCode.Exclude
    private final List<PartialFilmResponse> films;

    public ActorResponse(Actor actor) {
        this.id = actor.getId();
        this.firstName = actor.getFirstName();
        this.lastName = actor.getLastName();
        this.films = actor.getFilms().stream().map(PartialFilmResponse::new).toList();
    }

}
