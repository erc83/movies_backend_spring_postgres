package com.avanzado.movies_backend.repositories;

import com.avanzado.movies_backend.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(value = """
        select t from Token t inner join User u
        on t.user.id = u.id
        where u.id = :id and (t.isExpired = false or t.isRevoked = false)
        """)
    List<Token> findAllValidTokenByUser(Integer id);

    Optional<Token> findByToken(String token);
}
