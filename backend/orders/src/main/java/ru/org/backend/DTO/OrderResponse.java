package ru.org.backend.DTO;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import ru.org.backend.Models.Order;

@Data
@Builder
public class OrderResponse {

    private final HttpStatus status;
    private final Integer code;
    private final String message;
    private final Order order;

}
