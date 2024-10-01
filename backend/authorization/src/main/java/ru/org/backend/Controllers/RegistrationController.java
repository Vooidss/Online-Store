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
public class RegistrationController {

    private final RegistrationService registrationService;
    @PostMapping()
    public ResponseEntity<JwtAuthenticationResponse> createUser(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(registrationService.registration(request));
    }
}
