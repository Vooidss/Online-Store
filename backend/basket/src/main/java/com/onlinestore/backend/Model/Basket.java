package ru.org.basket.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "basket")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    private Integer productId;

    @Column(name = "countProduct", columnDefinition = "int DEFAULT 1")
    private Integer countProduct;

    @Column(name = "sizeProduct")
    private String sizeProduct;

    @Column(name = "stutus")
    private String status;
}
