package com.onlinestore.backend.Services;

import com.onlinestore.backend.Models.Short;
import com.onlinestore.backend.Repositories.ShortRepositories;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ShortService {
    private final ShortRepositories shortRepositories;

    public Map<String,Object> findAll(){

        return Map.of(
                "shorts", shortRepositories.findAll(),
                "isEmpty", shortRepositories.findAll().isEmpty()
        );
    }
    public Optional<Short> findById(int id) {
        return shortRepositories.findById(id);
    }

    public void setShort(Short shorts){
        shortRepositories.save(shorts);
    }

    public Optional<Short> deleteShort(int id){
        Optional<Short> deleteShort = findById(id);

        shortRepositories.deleteById(id);
        return deleteShort;
    }

}
