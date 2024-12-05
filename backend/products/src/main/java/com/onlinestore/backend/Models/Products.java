package com.onlinestore.backend.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.apache.kafka.common.serialization.Serializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "brand", nullable = false)
    private String brand;

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

    @Column(name = "priceDiscount", columnDefinition = "int default 0")
    @PositiveOrZero
    private int priceDiscount;

    @ManyToMany(mappedBy = "products")
    @JsonManagedReference
    private List<Sizes> sizes;

    @Override
    public String toString() {
        return "Products{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", brand='" + brand + '\'' +
                ", img='" + img + '\'' +
                ", description='" + description + '\'' +
                ", model='" + model + '\'' +
                ", material='" + material + '\'' +
                ", color='" + color + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", priceWithDiscount=" + priceWithDiscount +
                ", priceDiscount=" + priceDiscount +
                '}';
    }
}
