package ru.org.basket.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.org.basket.DTO.ProductInfoRequest;
import ru.org.basket.Model.Basket;
import ru.org.basket.Services.BasketService;
import ru.org.basket.Services.KafkaProducer;

@RestController
@Slf4j
@RequestMapping("/basket")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class BasketController {

    private final BasketService basketService;
    private final KafkaProducer kafkaProducer;

    @PostMapping("/save")
    public Basket addProductForUser(@RequestBody ProductInfoRequest request) {
        return basketService.save(request);
    }

    @PostMapping("/test/kafka")
    public void TestKafka(@RequestBody String message){
        kafkaProducer.sendMessage(
                new ProducerRecord<>(
                        "basket",
                        1,
                        "token",
                        message)
        );
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getProductsByUser(
        HttpServletRequest request
    ) {
        return basketService.getProductsByUser(request);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String,Object>> deleteProduct(@PathVariable(name = "id",required = false) int id,HttpServletRequest request){
        return basketService.deleteProduct(id,request);
    }

}
