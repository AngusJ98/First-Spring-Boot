package com.example.sakilademo.filmactor;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FilmActor {
    @Id
    short id;
}
