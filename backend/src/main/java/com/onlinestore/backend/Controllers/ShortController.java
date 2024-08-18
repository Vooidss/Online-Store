package com.onlinestore.backend.Controllers;

import com.onlinestore.backend.Models.Short;
import com.onlinestore.backend.Services.ShortService;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/short/v1")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ShortController {
    private final ShortService shortService;

    @GetMapping
    private Map<String,Object> getShorts(){
        return shortService.findAll();
    }

    @GetMapping("/{id}")
    private Map<String,Object> getSneaker(@PathVariable("id") int id){
        return Map.of("products",shortService.findById(id));
    }
}
