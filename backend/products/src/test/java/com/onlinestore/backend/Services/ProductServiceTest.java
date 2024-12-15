package com.onlinestore.backend.Services;

import com.onlinestore.backend.Models.Products;
import com.onlinestore.backend.Models.Sizes;
import com.onlinestore.backend.Repositories.ProductRepositories;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
@Slf4j
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepositories productRepositories;

    @Test
    void findAll(){
        //given
        List<Products> products =  List.of(
                new Products(
                        1, "sneakers", "Nike", "img", "Кроссовки","Модель", "кожа", "чёрные", 10000, 0, 10000, 0,
                        null
                ),
                new Products(
                        2, "sneakers", "Nike", "img", "Кроссовки","Модель", "кожа", "чёрные", 15000, 0, 15000, 0,
                        null
                ));
        Map<String,Object> response = Map.of(
            "products", products,
                "isEmpty", false
        );
        Mockito.doReturn(response).when(productRepositories).findAll();

        //when
        Map<String,Object> productsList = productService.findAll();

        //then
        Assertions.assertEquals(productsList,response);
    }

    @Test
    void findAllList_isNotEmpty() {
        //given
        List<Products> products =  List.of(
                new Products(
                1, "sneakers", "Nike", "img", "Кроссовки","Модель", "кожа", "чёрные", 10000, 0, 10000, 0,
                null
        ),
                new Products(
                        2, "sneakers", "Nike", "img", "Кроссовки","Модель", "кожа", "чёрные", 15000, 0, 15000, 0,
                        null
                ));
        Mockito.doReturn(products).when(productRepositories).findAll();

        //when
        List<Products> productsList = productService.findAllList();

        //then
        Assertions.assertEquals(productsList,products);
        log.info("Все впорядке");
    }

    @Test
    void findByType() {
    }

    @Test
    void save() {
    }

    @Test
    void deleteProduct() {
    }

    @Test
    void findAllById() {
    }

    @Test
    void findSpecificationsProducts() {
    }

    @Test
    void sortProducts() {
    }

    @Test
    void findProductById() {
    }
}