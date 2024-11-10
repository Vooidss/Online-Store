package com.onlinestore.backend.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonBackReference
    private List<Products> products;
}
