package com.onlinestore.backend.Models;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "sneaker")
@Getter
public class Sneaker {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "size")
    private int size;

    @Column(name = "img")
    private String img;
}
