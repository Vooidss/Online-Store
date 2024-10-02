package ru.org.backend.Models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.*;

@Table(name = "blackListTokens")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlackListTokens {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "token", nullable = false)
    private String token;
}
