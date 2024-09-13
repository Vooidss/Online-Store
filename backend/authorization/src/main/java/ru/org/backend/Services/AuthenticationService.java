package ru.org.backend.Services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
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
        JwtAuthenticationResponse jwtResponse = new JwtAuthenticationResponse();
        String error = null;

        authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
                  request.getLogin(),
                  request.getPassword()
          )
        );


        var user = userService.getByLogin(request.getLogin());

        if(user.getLogin() == null){
            throw new RuntimeException("Такого непользвателя нет");
        }

        try{
            jwt = jwtService.generateToken(new MyUserDetails(user));
        }catch (JwtGenerateTokenExceptions e){
            log.error(e.getMessage());
            return jwtResponse.setError(error);
        }

        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

}
