package com.example.sakilademo.actors;

import com.example.sakilademo.films.Film;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Unsigned;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter @Setter
@Table(name = "actor")
@AllArgsConstructor
public class Actor {

    @Id
    @Unsigned
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actor_id")
    private short id;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;


    @ManyToMany
    @JoinTable(
            name = "film_actor",
            joinColumns = {@JoinColumn(name = "actor_id")},
            inverseJoinColumns = {@JoinColumn(name = "film_id")}
    )
    private List<Film> films = new ArrayList<>();


    Actor() {

    }

    Actor(ActorInput input) {
        this.firstName = input.getFirstName();
        this.lastName = input.getLastName();
    }


}



