package ru.org.basket.Repositories;

import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.org.basket.Model.Basket;

public interface BasketRepositories extends JpaRepository<Basket, Integer> {
    List<Basket> findProductIdByUserId(int id);
    void deleteBasketByProductIdAndUserId(int productId, int userId);
    @Modifying
    @Transactional
    @Query("DELETE FROM Basket b WHERE b.userId = :userId")
    void deleteAllByUserId(@Param("userId") Integer userId);
}
