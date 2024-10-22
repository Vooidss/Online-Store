package ru.org.backend.DTO;

import lombok.*;
import ru.org.backend.user.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRequest{

    private String name;
    private String secondname;
    private int age;
    private String phone;
    private String sex;
}
