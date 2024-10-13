package ru.org.backend.DTO;

import lombok.*;
import ru.org.backend.user.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String name;
    private String secondName;
    private String login;
    private String password;
    private String email;
    private Role role;
    private int age;
    private String phone;
}
