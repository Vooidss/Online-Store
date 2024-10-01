package ru.org.backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.org.backend.Models.BlackListTokens;

import java.util.Optional;

public interface BlackListRepository extends JpaRepository<BlackListTokens,Integer> {
    Optional<BlackListTokens> findByToken(String token);
}
