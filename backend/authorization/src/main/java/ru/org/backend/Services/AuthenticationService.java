package ru.org.backend.Services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.org.backend.DTO.AuthenticationRequest;
import ru.org.backend.DTO.JwtAuthenticationResponse;
import ru.org.backend.Exceptions.JwtGenerateTokenExceptions;
import ru.org.backend.user.MyUserDetails;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    public JwtAuthenticationResponse authenticate(AuthenticationRequest request){

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
        }catch (BadCredentialsException e){
            error.append("Неверный учётные данные пользователя");
            code = 404;
            log.error(error.toString() + e.getMessage());
            isError = true;
        }

        if(!isError){
            var user = userService.getByLogin(request.getLogin());

            try{
                jwt = jwtService.generateToken(new MyUserDetails(user));
            }catch (JwtGenerateTokenExceptions e){
                log.error(e.getMessage());
            }
        }

        return JwtAuthenticationResponse.builder()
                .token(jwt)
                .error(error.toString())
                .code(code)
                .isFailed(isError)
                .build();
    }

}
