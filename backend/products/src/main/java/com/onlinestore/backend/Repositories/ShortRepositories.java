package com.onlinestore.backend.Repositories;

import com.onlinestore.backend.Models.Short;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortRepositories extends JpaRepository<Short, Integer> {}
