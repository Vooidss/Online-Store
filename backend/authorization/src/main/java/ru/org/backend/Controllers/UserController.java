package ru.org.backend.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.org.backend.Services.UserService;
import ru.org.backend.user.MyUser;

import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/id")
    public ResponseEntity<Map<String,Integer>> findUserIdByLogin(){
        return userService.findUserId();
    }

    @GetMapping("/current")
    public ResponseEntity<MyUser> getResponseCurrentUser(){
        return userService.getResponseCurrentUser();
    }
}
