package com.onlinestore.backend.Services;

import com.onlinestore.backend.Repositories.ProductRepositories;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class ProductServiceTest {


    @Mock
    private ProductRepositories productRepositories;
    private AutoCloseable  autoCloseable;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        autoCloseable =  MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepositories);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void findAll() {
    }

    @Test
    @Disabled
    void findByType() {
    }

    @Test
    @Disabled
    void save() {
    }

    @Test
    @Disabled
    void deleteProduct() {
    }

    @Test
    @Disabled
    void findAllById() {
    }
}