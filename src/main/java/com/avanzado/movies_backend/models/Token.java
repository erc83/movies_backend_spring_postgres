package com.avanzado.movies_backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tokens")
public final class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Usar estrategia IDENTITY para SERIAL en PostgreSQL
    private Integer id;

    @Column(unique = true, nullable = false) // El token es único y no puede ser nulo
    private String token;

    @Column(name = "token_type", nullable = false) // Ajustado para coincidir con el nombre de la columna en la tabla
    private String tokenType = "BEARER"; // Valor por defecto en caso de que no se especifique

    @Column(name = "is_revoked", nullable = false) // Ajustado el nombre de la columna
    private Boolean isRevoked;

    @Column(name = "is_expired", nullable = false) // Ajustado el nombre de la columna
    private Boolean isExpired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // Ajustado el nombre y especificado que no puede ser nulo
    private User user;

    // Constructor sin parámetros
    //public Token() {
    //}

    // Constructor con parámetros
    public Token(Integer id, String token, String tokenType, Boolean isRevoked, Boolean isExpired, User user) {
        this.id = id;
        this.token = token;
        this.tokenType = tokenType;
        this.isRevoked = isRevoked;
        this.isExpired = isExpired;
        this.user = user;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Boolean getIsRevoked() {
        return isRevoked;
    }
    public void setIsRevoked(Boolean isRevoked) {
        this.isRevoked = isRevoked;
    }

    public Boolean getIsExpired() {
        return isExpired;
    }
    public void setIsExpired(Boolean isExpired) {
        this.isExpired = isExpired;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}