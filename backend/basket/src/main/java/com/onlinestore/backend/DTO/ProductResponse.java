package com.onlinestore.backend.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {

    private int id;
    private String type;
    private String brand;
    private String size;
    private String img;
    private String description;
    private String model;
    private int price;
    private int discount;
    private int priceWithDiscount;

    private int basketId;
    private int count;
}
