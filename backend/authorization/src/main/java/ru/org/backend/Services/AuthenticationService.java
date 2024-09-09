package ru.org.backend.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.org.backend.DTO.AuthenticationRequest;
import ru.org.backend.DTO.JwtAuthenticationResponse;
import ru.org.backend.user.MyUserDetails;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    public JwtAuthenticationResponse authenticate(AuthenticationRequest request){
        System.out.println(request.getLogin() + " " + request.getPassword());

        authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
                  request.getLogin(),
                  request.getPassword()
          )
        );

        var user = userService.getByLogin(request.getLogin());
        var jwt = jwtService.generateToken(new MyUserDetails(user));

        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

}
