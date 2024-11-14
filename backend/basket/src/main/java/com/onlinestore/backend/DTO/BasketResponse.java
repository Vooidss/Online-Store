package ru.org.basket.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import ru.org.basket.Model.Basket;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
public class BasketResponse extends Response{
    private Basket basket;
}
