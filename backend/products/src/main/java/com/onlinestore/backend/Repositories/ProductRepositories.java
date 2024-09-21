package com.onlinestore.backend.Repositories;

import com.onlinestore.backend.Models.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepositories extends JpaRepository<Products,Integer> {
    public List<Products> findByType(String type);
    public Optional<Products> findByBrand(String brand);
    public Optional<Products> findBySize(String size);
}
