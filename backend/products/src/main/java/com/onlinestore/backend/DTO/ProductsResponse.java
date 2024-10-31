package com.onlinestore.backend.DTO;

import com.onlinestore.backend.Models.Products;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class ProductsResponse extends Response{

    private List<ProductDTO> products;

}
