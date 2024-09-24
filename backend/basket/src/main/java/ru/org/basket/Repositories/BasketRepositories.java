package ru.org.basket.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.org.basket.Model.Basket;

import java.util.List;

public interface BasketRepositories extends JpaRepository<Basket,Integer> {
    List<Basket> findProductIdByUserId(int id);
}
