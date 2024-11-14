package com.onlinestore.backend.Models;

import lombok.Getter;

@Getter
public enum BasketStatus {
    PURCHASED ("Куплен"),
    PROCESS_PURCHASED ("В процессе покупки");

    private final String title;

    BasketStatus(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Статус: " + title;
    }
}
