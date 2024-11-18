package com.onlinestore.backend.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.onlinestore.backend.DTO.BasketResponse;
import com.onlinestore.backend.DTO.ProductInfoRequest;
import com.onlinestore.backend.DTO.ResponseDTO;
import com.onlinestore.backend.Model.Basket;
import com.onlinestore.backend.Model.Status;
import com.onlinestore.backend.Services.BasketService;
import com.onlinestore.backend.Services.KafkaProducer;

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

    @GetMapping
    public ResponseEntity<Map<String, Object>> getProductsByUser(
        HttpServletRequest request
    ) {
        return basketService.getProductsByUser(request);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String,Object>> deleteProduct(@PathVariable(name = "id",required = false) int id, HttpServletRequest request){
        return basketService.deleteProduct(id,request);
    }

    @PatchMapping("/update/{id}/status/{status}")
    public ResponseEntity<BasketResponse> updateStatus(
            @PathVariable(name = "id") int id,
            @PathVariable(name = "status") String status){
        return basketService.updateStatus(id,status);
    }

    @PatchMapping("/update/{id}/{count}")
    public ResponseEntity<ResponseDTO<Map<String,Integer>>> updateCountsProductInBasket(
            @PathVariable(name = "id") int id,
            @PathVariable(name = "count") int count
    )  {
        return basketService.updateCountProductInBasket(id, count);
    }

}
