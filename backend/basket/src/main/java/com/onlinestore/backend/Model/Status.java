package ru.org.basket.Model;
import lombok.Getter;

@Getter
public enum Status {

    PURCHASED ("Куплен"),
    PROCESS_PURCHASED ("В процессе покупки");

    private final String title;

    Status(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Статус: " + title;
    }

}
