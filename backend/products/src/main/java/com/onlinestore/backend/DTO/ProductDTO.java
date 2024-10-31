package com.onlinestore.backend.DTO;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class ProductDTO {

    private int id;
    private String type;
    private String brand;
    private String img;
    private String description;
    private String model;
    private String material;;
    private String color;
    private int price;
    private int discount;
    private int priceWithDiscount;
    private int priceDiscount;
    private List<String> ListSizes;

}
