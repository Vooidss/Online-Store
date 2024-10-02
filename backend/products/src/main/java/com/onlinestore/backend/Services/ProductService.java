package com.onlinestore.backend.Services;

import com.onlinestore.backend.Models.Products;
import com.onlinestore.backend.Repositories.ProductRepositories;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepositories productRepositories;

    @Cacheable(value = "product", unless = "#result = null")
    public Map<String, Object> findAll() {
        List<Products> products = productRepositories.findAll();

        return Map.of("product", products, "isEmpty", products.isEmpty());
    }

    @Cacheable(value = "product", unless = "#result = null")
    public Map<String, Object> findByType(String type) {
        List<Products> products = productRepositories.findByType(type);

        return Map.of(type, products, "isEmpty", products.isEmpty());
    }

    public Products save(Products products) {
        return productRepositories.save(products);
    }

    public Optional<Products> deleteProduct(int id) {
        Optional<Products> deleteProduct = productRepositories.findById(id);

        productRepositories.deleteById(id);
        return deleteProduct;
    }

    public ResponseEntity<List<Products>> findAllById(List<Integer> ids) {
        List<Products> products = productRepositories.findAllById(ids);

        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(products);
    }
}
