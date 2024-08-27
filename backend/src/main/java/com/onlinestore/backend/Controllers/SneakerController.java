package com.onlinestore.backend.Controllers;


import com.onlinestore.backend.Services.SneakerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sneaker/v1")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class SneakerController {
    private final SneakerService sneakerService;

    @GetMapping
    private Map<String,Object> getSneakers(){
        return sneakerService.findAll();
    }

    @GetMapping("/{id}")
    private Map<String,Object> getSneaker(@PathVariable("id") int id){
        return Map.of("products",sneakerService.findById(id));
    }

}
