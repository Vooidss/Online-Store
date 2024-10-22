package ru.org.backend.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

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
    @Column(name = "login")
    private String login;

    @Column(name = "name")
    private String name;

    @Column(name = "secondname")
    private String secondname;

    @Column(name = "age")
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "email", unique = true)
    @Email
    private String email;

    @Column(name = "money")
    @Min(0)
    private int money;

    @Column(name = "phone")
    private String phone;

    @Column(name = "sex")
    private String sex;


    @Column(name = "date_registration")
    private LocalDateTime date_registration;
}
