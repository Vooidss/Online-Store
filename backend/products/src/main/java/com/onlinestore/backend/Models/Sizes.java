package com.onlinestore.backend.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Table(name = "sizes")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sizes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "size_value", nullable = false)
    private String size_value; //Например: S, XL, 38, 45

    @ManyToMany
    @JoinTable(
            name = "product_size",
            joinColumns = @JoinColumn(name = "size_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Products> products;
}
