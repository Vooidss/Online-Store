package ru.org.backend.Repositories;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.org.backend.Models.Order;
import ru.org.backend.Models.Status;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
