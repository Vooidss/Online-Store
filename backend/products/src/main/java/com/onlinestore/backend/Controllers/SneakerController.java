package com.onlinestore.backend.Controllers;


import com.onlinestore.backend.Models.Short;
import com.onlinestore.backend.Models.Sneaker;
import com.onlinestore.backend.Services.SneakerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/products/sneakers/v1")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class SneakerController {
    private final SneakerService sneakerService;

    @GetMapping
    private Map<String,Object> getSneakers(){
        return sneakerService.findAll();
    }

    @GetMapping("/{id}")
    private Map<String,Optional<Sneaker>> getSneaker(@PathVariable("id") int id){
        return sneakerService.findById(id);
    }

    @PostMapping()
    private Sneaker setShort(@RequestBody Sneaker sneaker){
        return sneakerService.setShort(sneaker);
    }

    @DeleteMapping("/{id}")
    private Optional<Sneaker> deleteShort(@PathVariable("id") int id){
        return sneakerService.deleteShort(id);
    }


}
