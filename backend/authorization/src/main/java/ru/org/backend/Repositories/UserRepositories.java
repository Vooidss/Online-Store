package ru.org.backend.Repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.org.backend.user.MyUser;

public interface UserRepositories extends JpaRepository<MyUser, Integer> {
    Optional<MyUser> findByName(String name);
    Optional<MyUser> findByLogin(String login);
    Optional<MyUser> findByEmail(String email);
    boolean existsByName(String name);
    boolean existsByLogin(String login);
    boolean existsByEmail(String email);
}
