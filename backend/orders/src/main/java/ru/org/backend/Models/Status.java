package ru.org.backend.Models;

public enum Status {

    SUCCESSFULLY ("Успешно"),
    IN_PROCESSING ("В процессе"),
    REJECTED ("Отклонен");

    private String title;

    Status(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Статус: " + title;
    }

}
