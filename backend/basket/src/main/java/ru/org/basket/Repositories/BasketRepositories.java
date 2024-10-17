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
    Basket findByUserIdAndProductId(int userId, int productId);
    @Transactional
    void deleteBasketByProductIdAndUserId(int productId, int userId);
    @Modifying
    @Transactional
    @Query("DELETE FROM Basket b WHERE b.userId = :userId")
    void deleteAllByUserId(@Param("userId") Integer userId);
    @Modifying
    @Transactional
    @Query("UPDATE Basket b SET b.countProduct = :count WHERE b.id = :basketId ")
    void updateCountProductById(@Param("basketId") int basketId, @Param("count") int count);

    @Query("SELECT b.countProduct FROM Basket b WHERE b.id = :id")
    Integer findCountProductById(@Param(value = "id") Integer id);
}
