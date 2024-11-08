package com.hukising.telegrambot.Models;

import lombok.Getter;

public enum DecorationProductStep {
    COUNT_PRODUCTS("Количество продуктов"),
    PRODUCT("Продукт");

    @Getter
    private final String message;

    DecorationProductStep(String message) {
        this.message = message;
    }
}
