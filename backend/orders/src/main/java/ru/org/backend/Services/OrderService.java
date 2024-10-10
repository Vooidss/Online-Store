package ru.org.backend.Services;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.org.backend.DTO.OrderRequest;

import java.util.Map;

@Service
@AllArgsConstructor
public class OrderService {

    private final KafkaProducer kafkaProducer;

    public ResponseEntity<Map<String, Object>> arrangeOrder(OrderRequest request, HttpServletRequest httpRequest) {

        String token = httpRequest.getHeader("Authorization").substring(7);

        kafkaProducer.sendMessage(token);

        return ResponseEntity.ok().body(Map.of("a","b"));
    }
}
