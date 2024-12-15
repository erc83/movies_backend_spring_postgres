package com.avanzado.movies_backend.services;

import com.avanzado.movies_backend.Dto.TokenResponse;
import com.avanzado.movies_backend.Dto.RegisterRequest;
import com.avanzado.movies_backend.models.User;
import com.avanzado.movies_backend.repositories.UserRepository;

import com.avanzado.movies_backend.repositories.TokenRepository;

import com.avanzado.movies_backend.Dto.AuthRequest;

import com.avanzado.movies_backend.models.Token;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public TokenResponse register(final RegisterRequest request) {
        var user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();

        var savedUser = userRepository.save(user);  // guardo al usuario en la base de datos

        //generar los tokens
        var jwtToken = jwtService.generateToken(savedUser);
        var refreshToken = jwtService.generateRefreshToken(savedUser);

        saveUserToken(savedUser, jwtToken);
        return new TokenResponse(jwtToken, refreshToken);
    }

    public TokenResponse authenticate(final AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        final User user = userRepository.findByEmail(request.email())
                .orElseThrow();
        final String accessToken = jwtService.generateToken(user);
        final String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        return new TokenResponse(accessToken, refreshToken);
    }

    private void saveUserToken(User user, String jwtToken) {
        // var token = Token.builder()
        Token token = Token.builder()
                .token(jwtToken)
                .tokenType(Token.TokenType.BEARER)
                .isExpired(false)
                .isRevoked(false)
                .user(user)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(final User user) {
        final List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (!validUserTokens.isEmpty()) {
            validUserTokens.forEach(token -> {
                token.setIsExpired(true);
                token.setIsRevoked(true);
            });
            tokenRepository.saveAll(validUserTokens);
        }
    }

    public TokenResponse refreshToken(@NotNull final String authentication) {

        if (authentication == null || !authentication.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid auth header");
        }

        final String refreshToken = authentication.substring(7);
        final String userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail == null) {
            throw new IllegalArgumentException("Invalid Refresh Token");
        }


        // verificamos si el token que se ha enviado el mismo que esta en la base de datos
        final User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new UsernameNotFoundException(userEmail));

        //final boolean isTokenValid = jwtService.isTokenValid(refreshToken, user);
        if(!jwtService.isTokenValid(refreshToken, user)) {
            throw new IllegalArgumentException("Invalid Refresh Token");
        }


        //final boolean isTokenValid = jwtService.isTokenValid(refreshToken, user);
        //if (!isTokenValid) {
        //    return null;
        //}

        final String accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);

        return new TokenResponse(accessToken, refreshToken);
    }
}
