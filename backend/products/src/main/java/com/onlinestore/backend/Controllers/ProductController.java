package com.onlinestore.backend.Controllers;

import com.onlinestore.backend.DTO.ProductDTO;
import com.onlinestore.backend.DTO.ProductResponse;
import com.onlinestore.backend.DTO.ProductsResponse;
import com.onlinestore.backend.DTO.SpecificationsResponse;
import com.onlinestore.backend.Models.Products;
import com.onlinestore.backend.Services.ProductService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    private Map<String, Object> findAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/sort/{type}")
    private ResponseEntity<ProductsResponse> sortProducts(
            @PathVariable("type") String type,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String material,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) String defaultSort,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice
    ){
        return productService.sortProducts(type,color,brand,material,size,defaultSort,minPrice,maxPrice);
    }

    @GetMapping("/{type}/specifications")
    private ResponseEntity<SpecificationsResponse> findSpecificationsProducts(
            @PathVariable("type") String type
    ){
        return productService.findSpecificationsProducts(type);
    }

    @GetMapping("/{type}")
    private ResponseEntity<ProductsResponse> findProductByType(
        @PathVariable("type") String type
    ) {
        return productService.findByType(type);
    }

    @PostMapping("/save")
    private ResponseEntity<ProductResponse> save(@RequestBody Products product) {
        return productService.save(product);
    }

    @DeleteMapping("/{id}")
    private Optional<Products> deleteShort(@PathVariable("id") int id) {
        return productService.deleteProduct(id);
    }

    @GetMapping("/id/{id}")
    private ResponseEntity<ProductResponse> findProductById(@PathVariable("id") int id) {
        return productService.findProductById(id);
    }

    @PostMapping("/ids")
    public ResponseEntity<List<ProductDTO>> findProductsByIds(
        @RequestBody List<Integer> ids
    ) {
        return productService.findAllById(ids);
    }
}
