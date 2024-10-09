package ru.org.backend.Controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.org.backend.DTO.OrderRequest;
import ru.org.backend.Services.OrderService;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/order")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/arrange")
    public ResponseEntity<Map<String,Object>> arrangeOrder(@RequestBody OrderRequest request){
        return orderService.arrangeOrder(request);
    }

}
