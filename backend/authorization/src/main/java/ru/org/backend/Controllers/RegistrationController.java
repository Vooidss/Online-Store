package ru.org.backend.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.org.backend.Services.MyUserDetailService;
import ru.org.backend.Services.UserService;
import ru.org.backend.user.MyUser;

@RestController
@RequestMapping("/registration")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class RegistrationController {

    private final UserService userService;
    @PostMapping("/user")
    public MyUser createUser(@RequestBody MyUser user){
        System.out.println(user);
        return userService.save(user);
    }

}
