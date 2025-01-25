package com.example.rest.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Assignee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
}
