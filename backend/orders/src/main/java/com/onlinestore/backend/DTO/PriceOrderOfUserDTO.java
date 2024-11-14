package com.onlinestore.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class PriceOrderOfUserDTO {
    private Integer userId;
    private Integer orderPrice;
}
