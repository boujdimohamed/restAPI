package com.example.rest.api.model;


import java.util.Objects;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Eindeutige ID des Tags

    private String label; // Name des Tags

    // Standardkonstruktor
    public Tag() {}

    // Konstruktor mit Parametern
    public Tag(String name) {
        this.label = name;
    }
}
