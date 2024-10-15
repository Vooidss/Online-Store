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

    @Column(name = "model")
    String model;

    @Column(name = "price")
    int price;
}
