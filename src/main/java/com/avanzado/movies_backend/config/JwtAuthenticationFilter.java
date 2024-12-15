package com.avanzado.movies_backend.config;

import com.avanzado.movies_backend.repositories.TokenRepository;

import com.avanzado.movies_backend.services.JwtService;
import com.avanzado.movies_backend.models.Token;
import com.avanzado.movies_backend.models.User;
import com.avanzado.movies_backend.repositories.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getServletPath().contains("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwtToken = authHeader.substring(7);
        final String userEmail = jwtService.extractUsername(jwtToken);
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (userEmail == null || authentication != null) {
            //filterChain.doFilter(request, response);
            return;
        }

        final Token token = tokenRepository.findByToken(jwtToken)
                                        .orElse(null);

        if(token == null || token.getIsExpired() || token.getIsRevoked()) {
            filterChain.doFilter(request, response);
            return;
        }                                        

                                        
        final UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
        final Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
        if(user.isEmpty()) {
            filterChain.doFilter(request, response);
        }

        final var authToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
        

        //final boolean isTokenExpiredOrRevoked = tokenRepository.findByToken(jwt)
        //        .map(token -> !token.getIsExpired() && !token.getIsRevoked())
        //        .orElse(false);
//
//
        //if (isTokenExpiredOrRevoked) {
        //    final Optional<User> user = userRepository.findByEmail(userEmail);
//
        //    if (user.isPresent()) {
        //        final boolean isTokenValid = jwtService.isTokenValid(jwt, user.get());
//
        //        if (isTokenValid) {
        //            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
        //                    userDetails,
        //                    null,
        //                    userDetails.getAuthorities()
        //            );
        //            authToken.setDetails(
        //                    new WebAuthenticationDetailsSource().buildDetails(request)
        //            );
        //            SecurityContextHolder.getContext().setAuthentication(authToken);
        //        }
        //    }
        //}

        //filterChain.doFilter(request, response);
    }
}
