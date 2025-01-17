package com.onlinestore.backend.util;

import com.onlinestore.backend.DTO.ProductDTO;
import com.onlinestore.backend.Models.Products;
import com.onlinestore.backend.Models.Sizes;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Slf4j
@Transactional
public class Mapper {
    private final ModelMapper modelMapper;

    @Transactional
    public ProductDTO mapProductInDTO(Products products){
        ProductDTO productDTO = modelMapper.map(products,ProductDTO.class);

        productDTO
                .setListSize(products.getSizes()
                    .stream()
                    .map(Sizes::getSize_value)
                        .sorted()
                        .collect(Collectors.toList())
                );
        return productDTO;
    }

    public List<ProductDTO> mapProductsInDTO(List<Products> products){
        return products.stream().map(this::mapProductInDTO).toList();
    }
}
