package ru.org.basket.Model;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class ProductInfoRequest {

    int productId;

    String token;

}
