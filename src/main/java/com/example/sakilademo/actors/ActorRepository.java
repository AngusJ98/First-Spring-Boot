package com.example.sakilademo.actors;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActorRepository extends JpaRepository <Actor, Short> {
    List<Actor> findAllByFirstName(String firstName);
    List<Actor> findAllByFirstNameContainsIgnoreCase(String firstName);
    Actor findById(short id);
}
