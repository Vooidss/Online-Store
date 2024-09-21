package ru.org.backend.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.org.backend.Services.UserService;
import ru.org.backend.user.MyUser;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    @GetMapping
    public MyUser getUser(){
        return userService.getCurrentUser();
    }
}
