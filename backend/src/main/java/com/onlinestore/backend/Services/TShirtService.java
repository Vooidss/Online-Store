package com.onlinestore.backend.Services;

import com.onlinestore.backend.Models.Short;
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
                "tshirts", tShirtRepositories.findAll(),
                "isEmpty", tShirtRepositories.findAll().isEmpty()
        );
    }

    public Map<String,Optional<TShirt>> findById(int id) {
        return Map.of("products",tShirtRepositories.findById(id));
    }

    public TShirt setShort(TShirt tShirt){
        tShirtRepositories.save(tShirt); return tShirt;
    }

    public Optional<TShirt> deleteShort(int id){
        Optional<TShirt> deleteShort = findById(id).get("products");

        tShirtRepositories.deleteById(id);
        return deleteShort;
    }
}
