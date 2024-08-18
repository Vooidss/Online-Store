package com.onlinestore.backend.Services;

import com.onlinestore.backend.Models.TShirt;
import com.onlinestore.backend.Repositories.TShirtRepositories;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TShirtService {
    private final TShirtRepositories tShirtRepositories;

    public Map<String,Object> findAll(){
        return Map.of(
                "shorts", tShirtRepositories.findAll(),
                "isEmpty", tShirtRepositories.findAll().isEmpty()
        );
    }

    public Optional<TShirt> findById(int id) {
        return tShirtRepositories.findById(id);
    }
}
