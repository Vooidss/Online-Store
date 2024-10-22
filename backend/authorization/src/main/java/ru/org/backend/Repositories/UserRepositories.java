package ru.org.backend.Repositories;

import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import ru.org.backend.user.MyUser;

public interface UserRepositories extends JpaRepository<MyUser, Integer> {
    Optional<MyUser> findByName(String name);
    Optional<MyUser> findByLogin(String login);
    Optional<MyUser> findByEmail(String email);

    @Query("SELECT money FROM MyUser WHERE id = :userId")
    Integer findMoneyById(@Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query("UPDATE MyUser u SET u.money = u.money + :amount WHERE u.id = :userId")
    void addToWallet(@Param("userId") Integer userId, @Param("amount") Integer amount);

    @Modifying
    @Transactional
    @Query("UPDATE MyUser u SET u.money = u.money - :orderPrice WHERE u.id = :userId")
    void writeOffMoney(@Param("userId") Integer userId, @Param("orderPrice") Integer orderPrice);

}
