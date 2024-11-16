package com.hukising.telegrambot.Models;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class Product {
    private int id;
    private String type;
    private String brand;
    private String img;
    private String description;
    private String model;
    private String material;
    private String color;
    private int price;
    private int discount;
    private List<Size> sizes;

    @Override
    public String toString() {
        return
                "id: " + id +
                "\n Тип продукта: " + type +
                "\n Бренд продукта: " + brand +
                "\n Ссылка на картинку: " + img +
                "\n Описание: " + description +
                "\n Модель: " + model +
                "\n Материал: " + material +
                "\n Цвет: " + color +
                "\n Цена: " + price +
                "\n Скидка: " + discount +
                "\n Размеры: " + sizes.stream().map(Size::getSize_value).toList();
    }
}
