package ru.org.backend.Config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.org.backend.Services.JwtService;
import ru.org.backend.Services.MyUserDetailService;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final MyUserDetailService userDetailService;

    @Override
    protected void doFilterInternal(
        @NotNull HttpServletRequest request,
        @NotNull HttpServletResponse response,
        @NotNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String path = request.getRequestURI();

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        String userLogin = "";

        if (
            authHeader == null ||
            !authHeader.startsWith("Bearer ") ||
            path.startsWith("/registration") ||
            path.startsWith("/authentication")
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);

        try {
            userLogin = jwtService.extractLogin(jwt);
        } catch (RuntimeException e) {
            System.out.println("Неверный токен");
            filterChain.doFilter(request, response);
            return;
        }
        if (
            userLogin != null &&
            SecurityContextHolder.getContext().getAuthentication() == null
        ) {
            UserDetails userDetails =
                this.userDetailService.loadUserByUsername(userLogin);

            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                    );
                authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                filterChain.doFilter(request, response);
                throw new RuntimeException("Проблема с JWT токеном");
            }
        }
        filterChain.doFilter(request, response);
    }
}
