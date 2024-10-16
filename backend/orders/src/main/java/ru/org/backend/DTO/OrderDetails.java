package ru.org.backend.DTO;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {

    private Integer userId;
    private Integer userMoney;
    private OrderRequest orderRequest;

}
