package ru.org.backend.Repositories;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.org.backend.Models.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
