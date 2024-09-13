package ru.org.backend.Controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.org.backend.Config.SecurityConfig;
import ru.org.backend.DTO.AuthenticationRequest;
import ru.org.backend.DTO.JwtAuthenticationResponse;
import ru.org.backend.Services.AuthenticationService;
import ru.org.backend.Services.UserService;

@RestController
@RequestMapping("/auth/authentication")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/user")
    public ResponseEntity<JwtAuthenticationResponse> authentication(
            @RequestBody AuthenticationRequest request){
        System.out.println(authenticationService.authenticate(request));
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }


}
