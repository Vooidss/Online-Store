package com.onlinestore.backend.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class OrderPriceOfUserDTO {
    private Integer userId;
    private Integer orderPrice;
}
