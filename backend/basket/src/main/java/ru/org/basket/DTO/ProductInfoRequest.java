package ru.org.basket.DTO;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class ProductInfoRequest {

    int productId;

    String token;

}
