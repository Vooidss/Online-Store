package ru.org.basket.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.org.basket.Model.Basket;

public interface BasketRepositories extends JpaRepository<Basket,Integer> {
}
