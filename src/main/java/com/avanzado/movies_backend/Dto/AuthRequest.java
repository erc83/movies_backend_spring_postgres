package com.avanzado.movies_backend.Dto;

public record AuthRequest(
        String email,
        String password
) {
}
