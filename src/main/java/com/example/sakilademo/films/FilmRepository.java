package com.example.sakilademo.films;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Short> {
    Film findById(short id);
}