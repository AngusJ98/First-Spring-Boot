package com.example.sakilademo.actors;

import com.example.sakilademo.films.Film;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Unsigned;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


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


    @ManyToMany(mappedBy = "cast")
    private List<Film> films = new ArrayList<>();


    Actor() {

    }

    Actor(ActorInput input) {
        this.firstName = input.getFirstName();
        this.lastName = input.getLastName();
    }

    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}



