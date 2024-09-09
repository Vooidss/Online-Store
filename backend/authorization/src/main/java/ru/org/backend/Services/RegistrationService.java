package ru.org.backend.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.org.backend.DTO.JwtAuthenticationResponse;
import ru.org.backend.DTO.RegisterRequest;
import ru.org.backend.Repositories.UserRepositories;
import ru.org.backend.user.MyUser;
import ru.org.backend.user.MyUserDetails;
import ru.org.backend.user.Role;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepositories userRepository;
    private final JwtService jwtService;

    public JwtAuthenticationResponse registration(RegisterRequest request){

        var user = MyUser.builder()
                .login(request.getLogin())
                .password(request.getPassword())
                .name(request.getName())
                .secondname(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .age(request.getAge())
                .role(request.getRole())
                .build();

        if(user.getRole() == null) {
            user.setRole(Role.USER);
        }

        System.out.println(user);
        userRepository.save(user);

        var jwt = jwtService.generateToken(new MyUserDetails(user));


        return JwtAuthenticationResponse
                .builder()
                .token(jwt)
                .build();
    }

}
