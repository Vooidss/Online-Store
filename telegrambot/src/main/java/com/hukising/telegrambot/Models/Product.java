package com.hukising.telegrambot.Models;

import lombok.*;

import java.util.List;

@Data
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
    private int priceWithDiscount;
    private int priceDiscount;
    private List<Size> sizes;

    @Override
    public String toString() {
        return
                "id: " + id +
                "\n Тип продукта: " + type +
                "\n Бренд продукта=" + brand +
                "\n Ссылка на картинку=" + img +
                "\n Описание: " + description +
                "\n Модель: " + model +
                "\n Материал: " + material +
                "\n Цвет: '" + color +
                "\n Цена: " + price +
                "\n Скидка: " + discount +
                "\n Цена со скидкой: " + priceWithDiscount +
                "\n Скидка в рублях: " + priceDiscount +
                "\n Размеры: " + sizes.stream().map(Size::getSize_value).toList();
    }
}
