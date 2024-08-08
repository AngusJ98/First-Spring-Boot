package com.example.sakilademo.actors;


import lombok.Getter;



@Getter
public class PartialActorResponse {
    private final short id;
    private final String firstName;
    private final String lastName;

    public PartialActorResponse(Actor actor) {
        this.id = actor.getId();
        this.firstName = actor.getFirstName();
        this.lastName = actor.getLastName();
    }
}
