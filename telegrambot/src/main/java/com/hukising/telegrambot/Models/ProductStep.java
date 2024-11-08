package com.hukising.telegrambot.Models;

import lombok.Getter;

public enum ProductStep {
    TYPE("Тип"),
    BRAND("Бренд"),
    MODEL("Модель"),
    IMAGE_URL("Ссылка на картинку"),
    MATERIAL("Материал"),
    SIZE("Размер"),
    DESCRIPTION("Описание"),
    COLOR("Цвет"),
    PRICE("Цена"),
    DISCOUNT("Скидка");

    @Getter
    private final String message;

    ProductStep(String message) {
        this.message = message;
    }
}
