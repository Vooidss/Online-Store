package com.onlinestore.backend.Repositories;

import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.onlinestore.backend.Model.Basket;

public interface BasketRepositories extends JpaRepository<Basket, Integer> {
    @Query("SELECT b FROM Basket b WHERE b.userId = :userId and b.status != 'Куплен'")
    List<Basket> findProductIdByUserId(@Param("userId") int userId);
    @Query("SELECT b FROM Basket b WHERE b.userId = :userId and b.productId = :productId and b.status != 'Куплен' ")
    Basket findByUserIdAndProductId(@Param("userId") int userId, @Param("productId") int productId);
    Basket findBasketById(int id);
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

    @Modifying
    @Transactional
    @Query("UPDATE Basket b SET b.status = :status WHERE b.userId = :id")
    void updateStatus(@Param("id") int id, @Param("status") String status);
}
