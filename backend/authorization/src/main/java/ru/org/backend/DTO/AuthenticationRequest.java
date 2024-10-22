package ru.org.backend.DTO;

import lombok.*;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest{

    private String login;
    private String password;
}
