package com.example.sakilademo.films;

import com.example.sakilademo.actors.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Actor, Short> {
    Film findById(short id);
}