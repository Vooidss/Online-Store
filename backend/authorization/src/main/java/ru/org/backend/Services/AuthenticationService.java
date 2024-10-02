package ru.org.backend.Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import ru.org.backend.DTO.AuthenticationRequest;
import ru.org.backend.DTO.JwtAuthenticationResponse;
import ru.org.backend.Exceptions.JwtGenerateTokenExceptions;
import ru.org.backend.Models.BlackListTokens;
import ru.org.backend.user.MyUser;
import ru.org.backend.user.MyUserDetails;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    public JwtAuthenticationResponse authenticate(
        AuthenticationRequest request
    ) {
        String jwt = null;
        int code = 200;
        StringBuilder error = new StringBuilder();
        boolean isError = false;

        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getLogin(),
                    request.getPassword()
                )
            );
        } catch (BadCredentialsException e) {
            error.append("Неверный учётные данные пользователя");
            code = 400;
            log.error(error.toString() + e.getMessage());
            isError = true;
        }

        if (!isError) {
            var user = userService.getByLogin(request.getLogin());
            MyUserDetails myUserDetails = MyUserDetails.builder()
                .user(user)
                .build();

            try {
                jwt = jwtService.generateToken(myUserDetails);
            } catch (JwtGenerateTokenExceptions e) {
                log.error(e.getMessage());
            }
        }

        return JwtAuthenticationResponse.generateJwtAuthenticationResponse(
            jwt,
            jwtService.isTokenExpired(jwt),
            error.toString(),
            code,
            isError
        );
    }

    public ResponseEntity<JwtAuthenticationResponse> isTokenExpired(
        HttpServletRequest request
    ) {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return ResponseEntity.ok(
                JwtAuthenticationResponse.generateJwtAuthenticationResponse(
                    null,
                    true,
                    "Пользователь ещё не вошёл.",
                    401,
                    true
                )
            );
        }

        String token = request.getHeader("Authorization").substring(7);

        if (jwtService.isTokenExpired(token)) {
            return ResponseEntity.ok(
                JwtAuthenticationResponse.generateJwtAuthenticationResponse(
                    token,
                    true,
                    null,
                    200,
                    false
                )
            );
        }

        return ResponseEntity.ok(
            JwtAuthenticationResponse.generateJwtAuthenticationResponse(
                token,
                false,
                null,
                200,
                false
            )
        );
    }

    public ResponseEntity<?> logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);

        jwtService.saveInvalidateToken(
            BlackListTokens.builder().token(token).build()
        );

        SecurityContextHolder.clearContext();

        return ResponseEntity.ok("Logout successful");
    }
}
