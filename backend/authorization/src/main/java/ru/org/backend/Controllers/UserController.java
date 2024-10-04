package ru.org.backend.Controllers;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.org.backend.Services.UserService;
import ru.org.backend.user.MyUser;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/id")
    public ResponseEntity<Map<String, Integer>> findUserIdByLogin() {
        return userService.findUserId();
    }

    @GetMapping("/current")
    public ResponseEntity<MyUser> getResponseCurrentUser() {
        return userService.getResponseCurrentUser();
    }

    @PatchMapping("/update")
    public ResponseEntity<Map<String,Object>> updateUser(MyUser myUser){
        return userService.updateUser(myUser);
    }

    @PatchMapping("/replenishment")
    public ResponseEntity<Map<String,Object>> replenishmentMoney(@NonNull @RequestBody Integer money){
        return userService.replenishmentMoney(money);
    }

}
