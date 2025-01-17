package com.onlinestore.backend.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.onlinestore.backend.DTO.OrderRequest;
import com.onlinestore.backend.DTO.OrderResponse;
import com.onlinestore.backend.Services.OrderService;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/order")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/arrange")
    public ResponseEntity<OrderResponse> arrangeOrder(@RequestBody OrderRequest request, HttpServletRequest httpRequest){
        return orderService.arrangeOrder(request,httpRequest);
    }

}
