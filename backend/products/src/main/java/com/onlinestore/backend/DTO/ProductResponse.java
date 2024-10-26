package com.onlinestore.backend.DTO;

import com.onlinestore.backend.Models.Products;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class ProductResponse extends Response{

    Products product;

}
