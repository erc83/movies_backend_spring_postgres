package com.avanzado.movies_backend.Dto;

public record RegisterRequest(
        String name,
        String email,
        String password
) {
}
