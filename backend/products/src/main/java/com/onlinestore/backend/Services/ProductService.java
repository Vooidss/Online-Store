package com.onlinestore.backend.Services;

import com.onlinestore.backend.DTO.SpecificationsResponse;
import com.onlinestore.backend.Models.Products;
import com.onlinestore.backend.Repositories.ProductRepositories;

import java.util.*;
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

    private List<Map<String, Object>> getCountSpecification(List<Object[]> specifications, String specificationName){

        List<Map<String,Object>> list = new ArrayList<>();

        for(Object[] object : specifications){
            if(object[0] instanceof String && object[0].equals(specificationName)){
                list.add(
                        Map.of(
                                "name", object[1],
                                "count", object[2]
                        )
                );
            }
        }
        return list;
    }

    private SpecificationsResponse buildResponse(List<Object[]> specifications) {

        List<Map<String,Object>> colors = getCountSpecification(specifications, "color");
        List<Map<String,Object>> brands = getCountSpecification(specifications, "brand");
        List<Map<String,Object>> sizes = getCountSpecification(specifications, "size");
        List<Map<String,Object>> materials = getCountSpecification(specifications, "material");

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

    public ResponseEntity<Map<String, Object>> sortProductByDefault(String type, String typeSort) {
        if(typeSort.equals("expensive")){
            return ResponseEntity.ok().body(
                    Map.of("products",
                    productRepositories.findDescPriceByType(type))
            );
        }else if(typeSort.equals("cheap")){
            return ResponseEntity.ok().body(
                    Map.of("products",
                            productRepositories.findAscPriceByType(type))
            );
        }
        return null;
    }

    public ResponseEntity<String> sortProductByMaterial(String type, String typeSort) {

        return null;
    }

    public ResponseEntity<String> sortProductBySize(String type, String typeSort) {

        return null;
    }

    public ResponseEntity<String> sortProductByColor(String type, String typeSort) {
        return null;
    }

    public ResponseEntity<String> sortProductByBrand(String type, String typeSort) {
        return null;
    }
}
