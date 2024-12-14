package com.onlinestore.backend.DTO;

import lombok.Data;

@Data
public class ProductForBasket implements Product{

    private int id;
    private String type;
    private String brand;
    private String img;
    private String description;
    private String model;
    private int price;
    private int discount;
    private int priceWithDiscount;
    private int priceDiscount;

}
