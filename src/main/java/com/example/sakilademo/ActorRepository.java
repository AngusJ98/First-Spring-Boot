package com.example.sakilademo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActorRepository extends JpaRepository <Actor, Short> {
    List<Actor> findAllByFirstName(String firstName);
    List<Actor> findAllByFirstNameContainsIgnoreCase(String firstName);
    Actor findById(short id);



}
