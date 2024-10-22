package ru.org.backend.user;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.ws.rs.DefaultValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class MyUser {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Пароль должен присутствовать обязательно")
    @Column(name = "password", nullable = false, unique = true)
    private String password;

    @NotNull(message = "Логин должен присутствовать обязательно")
    @Column(name = "login",nullable = false)
    private String login;

    @Column(name = "name",nullable = false)
    @NotEmpty
    private String name;

    @Column(name = "secondname")
    private String secondname;

    @Column(name = "age")
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(name = "role",nullable = false)
    private Role role;

    @Column(name = "email", unique = true, nullable = false)
    @Email
    private String email;

    @Column(name = "money", nullable = false)
    @PositiveOrZero
    private int money;

    @Column(name = "phone")
    @Pattern(regexp = "^\\d{5,15}$", message = "В номере должно присутствовать минимум 5 и максимум 15 цифр")
    private String phone;

    @Column(name = "sex")
    private String sex;


    @Column(name = "date_registration")
    private LocalDateTime date_registration;
}
