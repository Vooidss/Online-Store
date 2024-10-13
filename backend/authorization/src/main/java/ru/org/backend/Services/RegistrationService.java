package ru.org.backend.Services;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.org.backend.DTO.JwtAuthenticationResponse;
import ru.org.backend.DTO.RegisterRequest;
import ru.org.backend.Exceptions.JwtGenerateTokenExceptions;
import ru.org.backend.user.MyUser;
import ru.org.backend.user.MyUserDetails;
import ru.org.backend.user.Role;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;

    public ResponseEntity<JwtAuthenticationResponse> registration(RegisterRequest request) {
        String jwt = null;
        String error = null;

        if (request.getLogin() == null || request.getPassword() == null) {
            error = "Нет логина или пароля";
            log.error(error);
            return  ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(JwtAuthenticationResponse
                            .builder()
                            .error(error)
                            .code(HttpStatus.NOT_FOUND.value())
                            .build()
                    );
        }

        var user = MyUser.builder()
            .login(request.getLogin())
            .password(passwordEncoder.encode(request.getPassword()))
            .name(request.getName())
            .secondname(request.getSecondName())
            .email(request.getEmail())
            .age(request.getAge())
            .phone(request.getPhone())
            .role(request.getRole())
            .money(0)
            .date_registration(LocalDateTime.now())
            .build();

        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }

        try {
            userService.save(user);
        } catch (ConstraintViolationException e) {
            error = e
                .getConstraintViolations()
                .stream()
                .toList()
                .getFirst()
                .getMessage();
            log.error(error);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(JwtAuthenticationResponse
                            .builder()
                            .error(error)
                            .code(HttpStatus.BAD_REQUEST.value())
                            .build()
                    );
        } catch (DataIntegrityViolationException e) {
            error = e.getMostSpecificCause().getLocalizedMessage();
            log.error(error);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(JwtAuthenticationResponse
                            .builder()
                            .error(error)
                            .code(HttpStatus.BAD_REQUEST.value())
                            .build()
                    );
        }

        try {
            jwt = jwtService.generateToken(new MyUserDetails(user));
        } catch (JwtGenerateTokenExceptions e) {
            error = "Ошибка генерации токена";
            log.error(error);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(JwtAuthenticationResponse
                            .builder()
                            .error(error)
                            .code(HttpStatus.BAD_REQUEST.value())
                            .build()
                    );
        }

        return ResponseEntity.ok().body(
                JwtAuthenticationResponse
                        .builder()
                        .token(jwt)
                        .code(200)
                        .build()
        );
    }
}
