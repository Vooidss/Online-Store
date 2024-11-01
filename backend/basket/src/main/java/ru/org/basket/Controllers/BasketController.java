package ru.org.basket.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.org.basket.DTO.BasketResponse;
import ru.org.basket.DTO.ProductInfoRequest;
import ru.org.basket.DTO.ResponseDTO;
import ru.org.basket.Model.Basket;
import ru.org.basket.Model.Status;
import ru.org.basket.Services.BasketService;
import ru.org.basket.Services.KafkaProducer;

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
