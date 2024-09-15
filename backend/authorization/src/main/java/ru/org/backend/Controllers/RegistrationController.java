package ru.org.backend.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.org.backend.DTO.JwtAuthenticationResponse;
import ru.org.backend.DTO.RegisterRequest;
import ru.org.backend.Services.RegistrationService;


@RestController
@RequestMapping("/auth/registration")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class RegistrationController {

    private final RegistrationService registrationService;
    @PostMapping("/user")
    public ResponseEntity<JwtAuthenticationResponse> createUser(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(registrationService.registration(request));
    }

    @GetMapping("/user")
    public String avc(){
        return "asd";
    }

}
