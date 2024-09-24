package ru.org.basket.Controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.org.basket.Model.Basket;
import ru.org.basket.DTO.ProductInfoRequest;
import ru.org.basket.Services.BasketService;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/basket")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @PostMapping("/save")
    public Basket addProductForUser(@RequestBody ProductInfoRequest request) {
        return basketService.save(request);
    }

    @GetMapping()
    public ResponseEntity<Map<String,Object>> getProductsByUser(@RequestParam(value = "token",required = false) String token){
        return basketService.getProductsByUser(token);
    }

}
