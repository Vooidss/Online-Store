package ru.org.backend.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.org.backend.DTO.AuthenticationRequest;
import ru.org.backend.DTO.JwtAuthenticationResponse;
import ru.org.backend.Services.AuthenticationService;
import ru.org.backend.user.MyUser;

@RestController
@RequestMapping("/auth/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<JwtAuthenticationResponse> authentication(
        @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService. authenticate(request));
    }

    @GetMapping("/isTokenExpired")
    public ResponseEntity<JwtAuthenticationResponse> isValidToken(
        HttpServletRequest request
    ) {
        return authenticationService.isTokenExpired(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        return authenticationService.logout(request);
    }
}
