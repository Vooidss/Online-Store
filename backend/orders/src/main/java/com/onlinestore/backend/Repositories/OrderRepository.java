package com.onlinestore.backend.Repositories;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.onlinestore.backend.Models.Order;
import com.onlinestore.backend.Models.Status;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
