package com.avanzado.movies_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tokens")
public final class Token {
    
    public enum TokenType {
        BEARER
    }
    
    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "token_type")
    private TokenType tokenType = TokenType.BEARER;

    @Column(nullable = false , name = "revoked")
    private Boolean isRevoked;

    @Column(nullable = false, name = "expired")
    private Boolean isExpired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


}
