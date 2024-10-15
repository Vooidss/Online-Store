package com.onlinestore.backend.Controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinestore.backend.Models.Products;
import com.onlinestore.backend.Services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@MockMvcTest
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        objectMapper = new ObjectMapper();
    }

    @Test
    void saveProducts() throws Exception {
        Products product = Products
                .builder()
                .id(1)
                .brand("Nike")
                .model("Air force")
                .type("sneakers")
                .description("Кроссовки Nike")
                .price(9999)
                .img("https://avatars.mds.yandex.net/i?id=c83d5dbd29d5f70dcd5758a9646e4cf7_l-4033951-images-thumbs&n=13")
                .build();

        String productJson = objectMapper.writeValueAsString(product);

        mockMvc.perform(post("/products/save"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(""));
    }


}