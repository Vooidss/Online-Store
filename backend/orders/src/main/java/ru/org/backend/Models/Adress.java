package ru.org.backend.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.retry.annotation.CircuitBreaker;

import java.time.LocalDateTime;

@Entity
@Table(name = "adress")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Adress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "numberHouse")
    private Integer numberHouse;

    @Column(name = "numberApartment")
    private Integer numberApartment;

    @Column(name = "numberIntercom")
    private Integer numberIntercom;

    @Column(name = "created_at")
    private LocalDateTime created_at;
}
