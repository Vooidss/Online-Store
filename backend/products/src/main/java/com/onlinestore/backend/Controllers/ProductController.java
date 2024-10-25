package com.onlinestore.backend.Controllers;

import com.onlinestore.backend.DTO.SpecificationsResponse;
import com.onlinestore.backend.Models.Products;
import com.onlinestore.backend.Services.ProductService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
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

    @GetMapping("/sort/{type}/default/{typeSort}")
    private ResponseEntity<Map<String, Object>> sortProductByDefault(
            @PathVariable("type") String type,
            @PathVariable("typeSort") String typeSort
    ){
        return productService.sortProductByDefault(type, typeSort);
    }

    @GetMapping("/sort/{type}/material/{typeSort}")
    private ResponseEntity<String> sortProductByMaterial(
            @PathVariable("type") String type,
            @PathVariable("typeSort") String typeSort
    ){
        return productService.sortProductByMaterial(type, typeSort);
    }

    @GetMapping("/sort/{type}/size/{typeSort}")
    private ResponseEntity<String> sortProductBySize(
            @PathVariable("type") String type,
            @PathVariable("typeSort") String typeSort
    ){
        return productService.sortProductBySize(type, typeSort);
    }

    @GetMapping("/sort/{type}/color/{typeSort}")
    private ResponseEntity<String> sortProductByColor(
            @PathVariable("type") String type,
            @PathVariable("typeSort") String typeSort
    ){
        return productService.sortProductByColor(type, typeSort);
    }
    @GetMapping("/sort/{type}/brand/{typeSort}")
    private ResponseEntity<String> sortProductByBrand(
            @PathVariable("type") String type,
            @PathVariable("typeSort") String typeSort
    ){
        return productService.sortProductByBrand(type, typeSort);
    }



    @GetMapping("/{type}/specifications")
    private ResponseEntity<SpecificationsResponse> findSpecificationsProducts(
            @PathVariable("type") String type
    ){
        return productService.findSpecificationsProducts(type);
    }

    @GetMapping("/{type}")
    private Map<String, Object> findProductByType(
        @PathVariable("type") String type
    ) {
        return productService.findByType(type);
    }

    @PostMapping("/save")
    private Products save(@RequestBody Products products) {
        return productService.save(products);
    }

    @DeleteMapping("/{id}")
    private Optional<Products> deleteShort(@PathVariable("id") int id) {
        return productService.deleteProduct(id);
    }

    @PostMapping("/ids")
    public ResponseEntity<List<Products>> findProductsByIds(
        @RequestBody List<Integer> ids
    ) {
        return productService.findAllById(ids);
    }
}
