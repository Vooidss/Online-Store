package com.onlinestore.backend.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderRequest {
    private int orderPrice;
    private int discountPrice;
    private int resultPrice;
    private AdressDetails adress;
    private RecipientDetails recipient;
}
