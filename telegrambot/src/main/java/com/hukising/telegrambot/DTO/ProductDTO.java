package com.hukising.telegrambot.DTO;

import com.hukising.telegrambot.Models.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    List<Product> products;
}
