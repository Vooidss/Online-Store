package ru.org.backend.Config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.org.backend.Services.JwtService;
import ru.org.backend.Services.MyUserDetailService;
import ru.org.backend.Services.UserService;

import java.io.IOException;
import java.security.SignatureException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final MyUserDetailService userDetailService;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull @NonNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String path = request.getRequestURI();


        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        String userLogin = "";

        if(authHeader == null || !authHeader.startsWith("Bearer ") || path.startsWith("/auth")){
            filterChain.doFilter(request,response );
            return;
        }

        jwt = authHeader.substring(7);

        log.info(jwt);

        try {
            log.info("2");
            userLogin = jwtService.extractLogin(jwt);
            log.info("1");
        }catch (RuntimeException e){
            filterChain.doFilter(request,response );
            return;
        }
        log.info("2");

        if (jwtService.isTokenBlacklisted(jwt)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            log.error("Токен находится в Black List");
            return;
        }

        if(userLogin != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailService.loadUserByUsername(userLogin);

                if(jwtService.isTokenValid(jwt,userDetails)){
                    UsernamePasswordAuthenticationToken authToken  = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }else{
                    filterChain.doFilter(request,response);
                    throw new RuntimeException("Проблема с JWT токеном");
                }
        }
        filterChain.doFilter(request,response);
    }
}
