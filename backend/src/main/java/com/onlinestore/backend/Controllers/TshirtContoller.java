package com.onlinestore.backend.Controllers;

import com.onlinestore.backend.Models.Sneaker;
import com.onlinestore.backend.Models.TShirt;
import com.onlinestore.backend.Services.TShirtService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/tshirt/v1")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class TshirtContoller {
    private final TShirtService tShirtService;

    @GetMapping
    private Map<String,Object> getTShirts(){
        return tShirtService.findAll();
    }

    @GetMapping("/{id}")
    private Map<String, Optional<TShirt>> getSneaker(@PathVariable("id") int id){
        return tShirtService.findById(id);
    }

    @PostMapping()
    private TShirt setShort(@RequestBody TShirt tShirt){
        return tShirtService.setShort(tShirt);
    }

    @DeleteMapping("/{id}")
    private Optional<TShirt> deleteShort(@PathVariable("id") int id){
        return tShirtService.deleteShort(id);
    }

}
