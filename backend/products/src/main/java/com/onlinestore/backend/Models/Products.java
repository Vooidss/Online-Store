package com.onlinestore.backend.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
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

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "size", nullable = false)
    private String size;

    @Column(name = "img",nullable = false)
    private String img;

    @Column(name = "description",nullable = false)
    private String description;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "material", nullable = false)
    private String material;;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "price", nullable = false)
    @Positive
    @Min(1000)
    private int price;

    @Column(name = "discount",columnDefinition = "int default 0")
    @PositiveOrZero
    @Max(99)
    private int discount;

    @Column(name = "priceWithDiscount", columnDefinition = "int default 0")
    @PositiveOrZero
    private int priceWithDiscount;
}
