package com.onlinestore.backend.Services;

import com.onlinestore.backend.Models.ProductRequest;
import com.onlinestore.backend.Models.Short;
import com.onlinestore.backend.Repositories.TShirtRepositories;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TShirtService {

    private final TShirtRepositories tShirtRepositories;

    public Map<String, Object> findAll() {
        return Map.of(
            "tshirts",
            tShirtRepositories.findAll(),
            "isEmpty",
            tShirtRepositories.findAll().isEmpty()
        );
    }

    public Map<String, Optional<ProductRequest>> findById(int id) {
        return Map.of("products", tShirtRepositories.findById(id));
    }

    public ProductRequest setShort(ProductRequest tShirt) {
        tShirtRepositories.save(tShirt);
        return tShirt;
    }

    public Optional<ProductRequest> deleteShort(int id) {
        Optional<ProductRequest> deleteShort = findById(id).get("products");

        tShirtRepositories.deleteById(id);
        return deleteShort;
    }
}
