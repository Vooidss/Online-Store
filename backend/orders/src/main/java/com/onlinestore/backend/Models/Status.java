package com.onlinestore.backend.Models;

import lombok.Getter;

@Getter
public enum Status {

    SUCCESSFULLY ("Успешно"),
    REJECTED ("Отклонен");

    private final String title;

    Status(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Статус: " + title;
    }

}
