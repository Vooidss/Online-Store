package com.onlinestore.backend.Repositories;

import com.onlinestore.backend.Models.ProductRequest;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TShirtRepositories
    extends JpaRepository<ProductRequest, Integer> {}
