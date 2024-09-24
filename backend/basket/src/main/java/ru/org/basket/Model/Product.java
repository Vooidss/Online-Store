package ru.org.basket.Model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {
    private int id;
    private String type;
    private String brand;
    private String size;
    private String img;
    private String description;
    private String model;
    private int price;
}
