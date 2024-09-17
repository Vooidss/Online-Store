package com.onlinestore.backend.Repositories;

import com.onlinestore.backend.Models.Sneaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SneakerRepositories extends JpaRepository<Sneaker, Integer> {}
