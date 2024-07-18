package com.example.sakilademo.language;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "language")
@Data
@NoArgsConstructor
public class Language {

    @Id
    @Column(name = "language_id")
    private byte id;

    @Column(name = "name")
    private String name;

    public String toString() {
        return (this.name);
    }
}
