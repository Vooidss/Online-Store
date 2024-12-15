package com.onlinestore.backend.Services;

import com.onlinestore.backend.DTO.ProductDTO;
import com.onlinestore.backend.DTO.ProductResponse;
import com.onlinestore.backend.DTO.ProductsResponse;
import com.onlinestore.backend.DTO.SpecificationsResponse;
import com.onlinestore.backend.Models.Products;
import com.onlinestore.backend.Models.Sizes;
import com.onlinestore.backend.Repositories.ProductRepositories;

import java.util.*;

import com.onlinestore.backend.util.Mapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepositories productRepositories;
    private final Mapper mapper;

    public Map<String, Object> findAll() {
        List<Products> products = findAllList();

        return Map.of("product", products, "isEmpty", products.isEmpty());
    }

    public List<Products> findAllList() {
        return productRepositories.findAll();
    }

    public ResponseEntity<ProductsResponse> findByType(String type) {
        List<ProductDTO> products = mapper.mapProductsInDTO(productRepositories.findByType(type));

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
            product.setPriceDiscount(product.getDiscount() * product.getPrice() / 100);
            product.setPriceWithDiscount(product.getPrice() - product.getPriceDiscount());
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

        ProductDTO productDTO = mapper.mapProductInDTO(product);

        ProductResponse productsResponse = ProductResponse
                .builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.value())
                .message("Продукт успешно сохранён")
                .product(productDTO)
                .build();

        return ResponseEntity.ok().body(productsResponse);
    }

    public Optional<Products> deleteProduct(int id) {
        Optional<Products> deleteProduct = productRepositories.findById(id);

        productRepositories.deleteById(id);
        return deleteProduct;
    }

    public ResponseEntity<List<ProductDTO>> findAllById(List<Integer> ids) {
        List<ProductDTO> products = mapper.mapProductsInDTO(productRepositories.findAllById(ids));

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

    private SpecificationsResponse buildResponse(List<Object[]> specifications, String type)    {

        List<Map<String,Object>> colors = getCountSpecification(specifications, "color");
        List<Map<String,Object>> brands = getCountSpecification(specifications, "brand");
        List<Map<String,Object>> sizes = getCountSpecification(specifications, "size");
        List<Map<String,Object>> materials = getCountSpecification(specifications, "material");
        Integer minPrice = productRepositories.findProductWithMinPriceByType(type);
        Integer maxPrice = productRepositories.findProductWithMaxPriceByType(type);

        return SpecificationsResponse
                .builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.value())
                .message("Все характеристики продукта и их количество получены")
                .colors(colors)
                .brands(brands)
                .sizes(sizes)
                .materials(materials)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .build();
    }


    public ResponseEntity<SpecificationsResponse> findSpecificationsProducts(String type) {

        try {

            List<Object[]> specifications = productRepositories.findSpecificationsProducts(type);
            specifications.forEach(System.out::println);
            SpecificationsResponse specificationsResponse = buildResponse(specifications, type);
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

    public ResponseEntity<ProductsResponse> sortProducts(String typeProduct, String color, String brand,
                                                         String material, List<String> size, String defaultSort,
                                                         Integer minPrice, Integer maxPrice) {
        List<Products> products = switch (defaultSort) {
            case "cheap" -> productRepositories.findAscPriceByType(typeProduct);
            case "discount" -> productRepositories.findOnlyDiscount(typeProduct);
            case "expensive" -> productRepositories.findDescPriceByType(typeProduct);
            case null, default -> productRepositories.findByType(typeProduct);
        };

        products = products.stream()
                .filter(product ->
                                (color == null || color.contains(product.getColor())) &&
                                (brand == null || brand.contains(product.getBrand())) &&
                                (material == null || material.contains(product.getMaterial())) &&
                                (size == null || product.getSizes().stream().map(Sizes::getSize_value).anyMatch(size::contains)) &&
                                (minPrice == null || product.getPriceWithDiscount() >= minPrice) &&
                                (maxPrice == null || product.getPriceWithDiscount() <= maxPrice)
                )
                .toList();

        List<ProductDTO> productsDTO = mapper.mapProductsInDTO(products);

        ProductsResponse productsResponse = ProductsResponse
                .builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.value())
                .message("Продукты по заданным характеристикам получены")
                .products(productsDTO)
                .build();

        return ResponseEntity.ok().body(productsResponse);
    }

    public ResponseEntity<ProductResponse> findProductById(int id) {

        Optional<ProductDTO> productDTO;

        try{
            productDTO = Optional.ofNullable(
                    mapper.mapProductInDTO(
                            productRepositories.findById(id).orElseThrow()
                    )
            );
        }catch (RuntimeException e){
            log.error(e.getMessage());
            log.error(Arrays.toString(e.getStackTrace()));
            log.error("Ошибка при получении ID пользовтеля");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ProductResponse
                            .builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .code(HttpStatus.BAD_REQUEST.value())
                            .message("Ошибка при получении ID пользовтеля")
                            .product(null)
                            .build()
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                ProductResponse
                        .builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .message("Продукт по ID успешно получен")
                        .product(productDTO.orElseThrow())
                        .build()
        );
    }
}
