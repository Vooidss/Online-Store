package com.onlinestore.backend.Controllers;

import com.onlinestore.backend.Models.TShirt;
import com.onlinestore.backend.Services.TShirtService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    private Map<String,Object> getSneaker(@PathVariable("id") int id){
        return Map.of("products",tShirtService.findById(id));
    }

}
