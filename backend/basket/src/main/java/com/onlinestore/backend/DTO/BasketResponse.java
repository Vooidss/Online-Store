package com.onlinestore.backend.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import com.onlinestore.backend.Model.Basket;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
public class BasketResponse extends Response{
    private Basket basket;
}
