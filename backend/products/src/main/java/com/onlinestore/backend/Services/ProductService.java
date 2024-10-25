package com.onlinestore.backend.Services;

import com.onlinestore.backend.DTO.SpecificationsResponse;
import com.onlinestore.backend.Models.Products;
import com.onlinestore.backend.Repositories.ProductRepositories;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepositories productRepositories;

    @Cacheable(value = "product", unless = "#result = null")
    public Map<String, Object> findAll() {
        List<Products> products = productRepositories.findAll();

        return Map.of("product", products, "isEmpty", products.isEmpty());
    }

    @Cacheable(value = "product", unless = "#result = null")
    public Map<String, Object> findByType(String type) {
        List<Products> products = productRepositories.findByType(type);

        return Map.of(type, products, "isEmpty", products.isEmpty());
    }

    public Products save(Products products) {
        return productRepositories.save(products);
    }

    public Optional<Products> deleteProduct(int id) {
        Optional<Products> deleteProduct = productRepositories.findById(id);

        productRepositories.deleteById(id);
        return deleteProduct;
    }

    public ResponseEntity<List<Products>> findAllById(List<Integer> ids) {
        List<Products> products = productRepositories.findAllById(ids);

        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(products);
    }

    private Map<String,Long> getCountSpecification(List<Object[]> specifications, String specificationName){
        return specifications.stream().filter(x -> x[0].equals(specificationName))
                .collect(Collectors.toMap(x -> (String) x[1], x -> (Long) x[2]));
    }

    private SpecificationsResponse buildResponse(List<Object[]> specifications) {

        Map<String,Long> colors = getCountSpecification(specifications, "color");
        Map<String,Long> brands = getCountSpecification(specifications, "brand");
        Map<String,Long> sizes = getCountSpecification(specifications, "size");
        Map<String,Long> materials = getCountSpecification(specifications, "material");

        return SpecificationsResponse
                .builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.value())
                .message("Все характеристики продукта и их количество получены")
                .colors(colors)
                .brands(brands)
                .sizes(sizes)
                .materials(materials)
                .build();
    }


    public ResponseEntity<SpecificationsResponse> findSpecificationsProducts(String type) {

        try {

            List<Object[]> specifications = productRepositories.findSpecificationsProducts(type);
            SpecificationsResponse specificationsResponse = buildResponse(specifications);
            return ResponseEntity.ok().body(specificationsResponse);

        }catch (RuntimeException e){

            log.error(e.getMessage());

            return ResponseEntity.ok().body(SpecificationsResponse
                    .builder()
                            .status(HttpStatus.NOT_FOUND)
                            .code(HttpStatus.NOT_FOUND.value())
                            .message("Ошибка при получении характеристик продукта.")
                    .build()
            );
        }
    }
}
