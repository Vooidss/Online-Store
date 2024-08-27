package com.onlinestore.backend.Services;

import com.onlinestore.backend.Models.Sneaker;
import com.onlinestore.backend.Repositories.SneakerRepositories;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SneakerService {
    private final SneakerRepositories sneakerRepositories;

    public Map<String,Object> findAll(){
        return Map.of(
                "sneakers", sneakerRepositories.findAll(),
                "isEmpty", sneakerRepositories.findAll().isEmpty()
        );
    }
    public Optional<Sneaker> findById(int id) {
        return sneakerRepositories.findById(id);
    }
}
