package com.onlinestore.backend.Services;

import com.onlinestore.backend.Models.Short;
import com.onlinestore.backend.Repositories.ShortRepositories;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ShortService {
    private final ShortRepositories shortRepositories;


    @Cacheable(value = "short", unless = "#result = null")
    public Map<String,Object> findAll(){

        return Map.of(
                "shorts", shortRepositories.findAll(),
                "isEmpty", shortRepositories.findAll().isEmpty()
        );
    }
    @Cacheable(value = "short", unless = "#result = null")
    public Map<String,Optional<Short>> findById(int id) {
        return Map.of("products",shortRepositories.findById(id));
    }

    public Short setShort(Short shorts){
        shortRepositories.save(shorts);
        return shorts;
    }

    public Optional<Short> deleteShort(int id){
        Optional<Short> deleteShort = findById(id).get("products");

        shortRepositories.deleteById(id);
        return deleteShort;
    }

}
