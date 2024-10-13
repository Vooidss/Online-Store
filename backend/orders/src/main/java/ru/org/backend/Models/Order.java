package ru.org.backend.Models;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "adressId")
    private Integer adressId;

    @Column(name = "nameRecipient")
    private String nameRecipient;

    @Column(name = "secondNameRecipient")
    private String secondNameRecipient;

    @Column(name = "phoneRecipient")
    private String phoneRecipient;

    @Column(name = "orderPrice")
    private Integer orderPrice;

    @Column(name = "discountPrice")
    private Integer discountPrice;

    @Column(name = "resultPrice")
    private Integer resultPrice;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "status", nullable = false)
    private String status;
}
