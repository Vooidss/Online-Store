package com.onlinestore.backend.Services;

import com.onlinestore.backend.DTO.ProductResponse;
import com.onlinestore.backend.DTO.ProductsResponse;
import com.onlinestore.backend.DTO.SpecificationsResponse;
import com.onlinestore.backend.Models.Products;
import com.onlinestore.backend.Repositories.ProductRepositories;

import java.util.*;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepositories productRepositories;
    private final ObjectMapper objectMapper;

    @Cacheable(value = "product", unless = "#result = null")
    public Map<String, Object> findAll() {
        List<Products> products = productRepositories.findAll();

        return Map.of("product", products, "isEmpty", products.isEmpty());
    }

    private void addDiscountProduct(List<Products> products){
        products.stream().filter(x-> x.getDiscount() != 0).forEach(x -> x.setDiscount(x.getDiscount() * x.getPrice() / 100));
        products.stream().filter(x-> x.getDiscount() != 0).forEach(x -> x.setPriceWithDiscount(x.getPrice() - x.getDiscount()));
    }

    @Cacheable(value = "product", unless = "#result = null")
    public ResponseEntity<ProductsResponse> findByType(String type) {
        List<Products> products = productRepositories.findByType(type);
        addDiscountProduct(products);

        ProductsResponse productsResponse = ProductsResponse
                .builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.value())
                .message("Продукты успешно были возвращены")
                .products(products)
                .build();

        return ResponseEntity.ok().body(productsResponse);
    }

    public ResponseEntity<ProductResponse> save(Products product) {

        try {
            product = productRepositories.save(product);
        }catch (RuntimeException e){
            log.error(e.getMessage());
            log.error(Arrays.toString(e.getStackTrace()));

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ProductResponse
                            .builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .code(HttpStatus.BAD_REQUEST.value())
                            .message("Продукт не сохранён. Ошибка при получении данных")
                            .product(null)
                            .build()
            );

        }


        ProductResponse productsResponse = ProductResponse
                .builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.value())
                .message("Продукт успешно сохранён")
                .product(product)
                .build();

        return ResponseEntity.ok().body(productsResponse);
    }

    public Optional<Products> deleteProduct(int id) {
        Optional<Products> deleteProduct = productRepositories.findById(id);

        productRepositories.deleteById(id);
        return deleteProduct;
    }

    public ResponseEntity<List<Products>> findAllById(List<Integer> ids) {
        List<Products> products = productRepositories.findAllById(ids);
        addDiscountProduct(products);

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

    public ResponseEntity<ProductsResponse> sortProducts(String typeProduct, String color, String brand, String material, String size, String defaultSort) {
        List<Products> products = switch (defaultSort) {
            case "cheap" -> productRepositories.findAscPriceByType(typeProduct);
            case "discount" -> productRepositories.findOnlyDiscount(typeProduct);
            case "expensive" -> productRepositories.findDescPriceByType(typeProduct);
            case null, default -> productRepositories.findByType(typeProduct);
        };

        products = products
                .stream()
                .filter(product ->
                        (color == null || color.contains(product.getColor())) &&
                        (brand == null || brand.contains(product.getBrand())) &&
                        (material == null || material.contains(product.getMaterial())) &&
                        (size == null || size.contains(product.getSize()))
                )
                .toList();

        addDiscountProduct(products);

        ProductsResponse productsResponse = ProductsResponse
                .builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.value())
                .message("Продукты по заданным характеристикам получены")
                .products(products)
                .build();

        return ResponseEntity.ok().body(productsResponse);
    }
}
