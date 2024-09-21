package com.onlinestore.backend.Models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;

@Entity
@Table(name = "products")
@Getter
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "type")
    String type; // Например: футболки, шорты...

    @Column(name = "brand")
    String brand;

    @Column(name = "size")
    String size;

    @Column(name = "img")
    String img;

    @Column(name = "description")
    String description;

    @Column(name = "price")
    int price;

    @Column(name = "material")
    String material;
}
