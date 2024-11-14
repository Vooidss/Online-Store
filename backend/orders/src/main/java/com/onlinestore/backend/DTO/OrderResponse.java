package com.onlinestore.backend.DTO;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import com.onlinestore.backend.Models.Order;

@Data
@Builder
public class OrderResponse {

    private final HttpStatus status;
    private final Integer code;
    private final String message;
    private final Order order;

}
