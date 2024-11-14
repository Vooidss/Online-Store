package com.onlinestore.backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.onlinestore.backend.Models.Adress;

public interface AdressRepository extends JpaRepository<Adress,Integer> {
}
