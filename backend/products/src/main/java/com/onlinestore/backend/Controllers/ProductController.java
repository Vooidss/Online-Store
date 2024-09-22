package com.onlinestore.backend.Controllers;

import com.onlinestore.backend.Models.Products;
import com.onlinestore.backend.Services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
    private final ProductService productService;
    @GetMapping
    private Map<String, Object> findAllProducts(){
        return productService.findAll();
    }

    @GetMapping("/{type}")
    private Map<String, Object> findProductByType(@PathVariable("type") String type){
        return productService.findByType(type);
    }

    @PostMapping("/save")
    private Products save(@RequestBody Products products){
        return productService.save(products);
    }

    @DeleteMapping("/{id}")
    private Optional<Products> deleteShort(@PathVariable("id") int id){
        return productService.deleteProduct(id);
    }
}
