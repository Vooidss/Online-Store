package com.onlinestore.backend.Services;

import com.onlinestore.backend.DTO.ProductDTO;
import com.onlinestore.backend.Models.Products;
import com.onlinestore.backend.Models.Sizes;
import com.onlinestore.backend.Repositories.ProductRepositories;
import com.onlinestore.backend.util.Mapper;
import lombok.extern.flogger.Flogger;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepositories productRepositories;

    @Mock
    private Mapper mapper;

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
                "isEmpty", false,
                "products", products
        );
        Mockito.doReturn(products).when(productRepositories).findAll();

        //when
        Map<String,Object> productsList = productService.findAll();

        //then
        Assertions.assertEquals(true,true);
    }

    @Test
    void findByType() {
    }

    @Test
    void save() {
        //given
       var products =  new Products(
                1, "sneakers", "Nike", "img", "Кроссовки","Модель", "кожа", "чёрные", 10000, 0, 10000, 0,
                null
        );
       var productDTO = mapper.mapProductInDTO(products);
        Mockito.doReturn(products).when(productRepositories).save(products);

        //when
        var response = productService.save(products);

        //then
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertEquals(productDTO, Objects.requireNonNull(response.getBody()).getProduct());
    }

    @Test
    void findAllById() {
    }

    @Test
    void findSpecificationsProducts() {
    }

    @Test
    void sortProducts_WhenComesOneParameter() {
        //given
        List<Products> products =  List.of(
                new Products(
                        1, "sneakers", "Nike", "img", "Кроссовки","Модель", "кожа", "черные", 10000, 0, 10000, 0,
                        null
                ),
                new Products(
                        2, "sneakers", "Nike", "img", "Кроссовки","Модель", "кожа", "черные", 15000, 0, 15000, 0,
                        null
                ),
                new Products(
                        3, "sneakers", "Nike", "img", "Кроссовки","Модель", "кожа", "серые", 15000, 0, 15000, 0,
                        null
                )
        );
        List<ProductDTO> result = mapper.mapProductsInDTO(List.of(
                new Products(
                        1, "sneakers", "Nike", "img", "Кроссовки","Модель", "кожа", "черные", 10000, 0, 10000, 0,
                        null
                ),
                new Products(
                        2, "sneakers", "Nike", "img", "Кроссовки","Модель", "кожа", "черные", 15000, 0, 15000, 0,
                        null
                )
        ));
        String color = "черные", type = "sneakers";
        Mockito.doReturn(products).when(productRepositories).findByType(type);

        //when
        var response = productService.sortProducts(type,color,null,null,null,null,null,null);

        //then
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertEquals(result,response.getBody().getProducts());
    }

    @Test
    void findProductById() {
        // given
        int input_id = 1;
        var products = new Products(
                1, "sneakers", "Nike", "img", "Кроссовки", "Модель", "кожа", "чёрные", 10000, 0, 10000, 0,
                null
        );
        var productDTO = mapper.mapProductInDTO(products);
        Mockito.doReturn(Optional.of(products)).when(productRepositories).findById(input_id);

        // when
        var response = productService.findProductById(input_id);

        // then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(productDTO, response.getBody().getProduct());
    }

}