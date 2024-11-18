package com.onlinestore.backend.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductInfoRequest {

    int productId;
    String token;
    String sizeProduct;
}
