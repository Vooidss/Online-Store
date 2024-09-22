package ru.org.backend.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.org.backend.Services.UserService;
import ru.org.backend.user.MyUser;

import java.util.Map;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    @GetMapping("/id")
    public ResponseEntity<Map<String,Integer>> findUserIdByLogin(){
        return userService.findUserId();
    }
}
