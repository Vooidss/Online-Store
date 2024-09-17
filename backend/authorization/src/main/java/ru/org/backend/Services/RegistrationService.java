package ru.org.backend.Services;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.org.backend.DTO.JwtAuthenticationResponse;
import ru.org.backend.DTO.RegisterRequest;
import ru.org.backend.Exceptions.JwtGenerateTokenExceptions;
import ru.org.backend.user.MyUser;
import ru.org.backend.user.MyUserDetails;
import ru.org.backend.user.Role;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;

    public JwtAuthenticationResponse registration(RegisterRequest request) {
        JwtAuthenticationResponse jwtResponse = new JwtAuthenticationResponse();
        String jwt = null;
        String error = null;

        if (request.getLogin() == null || request.getPassword() == null) {
            error = "Нет логина или пароля";
            log.error(error);
            return jwtResponse.setError(error);
        }
        var user = MyUser.builder()
            .login(request.getLogin())
            .password(passwordEncoder.encode(request.getPassword()))
            .name(request.getName())
            .secondname(request.getSecondName())
            .email(request.getEmail())
            .age(request.getAge())
            .role(request.getRole())
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
            return jwtResponse.setError(error);
        } catch (DataIntegrityViolationException e) {
            error = e.getMostSpecificCause().getLocalizedMessage();
            log.error(error);
            return jwtResponse.setError(error);
        }

        //TODO: Доделать JwtGenerateTokenExceptions ( По желанию )
        try {
            jwt = jwtService.generateToken(new MyUserDetails(user));
        } catch (JwtGenerateTokenExceptions e) {
            error = "Ошибка генерации токена";
            log.error(error);
            return jwtResponse.setError(error);
        }

        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}
