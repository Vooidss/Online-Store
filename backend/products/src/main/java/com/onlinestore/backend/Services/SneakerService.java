package com.onlinestore.backend.Services;

import com.onlinestore.backend.Models.Short;
import com.onlinestore.backend.Models.Sneaker;
import com.onlinestore.backend.Repositories.SneakerRepositories;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SneakerService {

    private final SneakerRepositories sneakerRepositories;

    public Map<String, Object> findAll() {
        return Map.of(
            "sneakers",
            sneakerRepositories.findAll(),
            "isEmpty",
            sneakerRepositories.findAll().isEmpty()
        );
    }

    public Map<String, Optional<Sneaker>> findById(int id) {
        return Map.of("products", sneakerRepositories.findById(id));
    }

    public Sneaker setShort(Sneaker sneaker) {
        sneakerRepositories.save(sneaker);
        return sneaker;
    }

    public Optional<Sneaker> deleteShort(int id) {
        Optional<Sneaker> deleteShort = findById(id).get("products");

        sneakerRepositories.deleteById(id);
        return deleteShort;
    }
}
