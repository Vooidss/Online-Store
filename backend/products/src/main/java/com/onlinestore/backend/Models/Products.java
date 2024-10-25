package com.onlinestore.backend.Models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "products")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "type")
    private String type;

    @Column(name = "brand")
    private String brand;

    @Column(name = "size")
    private String size;

    @Column(name = "img")
    private String img;

    @Column(name = "description")
    private String description;

    @Column(name = "model")
    private String model;

    @Column(name = "material")
    private String material;;

    @Column(name = "color")
    private String color;

    @Column(name = "price")
    private int price;
}
