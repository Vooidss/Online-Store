package com.onlinestore.backend.Services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Test
    void arrangeOrder() {
    }

    @Test
    void isEnoughMoney_WhenReturnTrue() {
        //given
        Integer userMonet =  10000;
        Integer orderMoney = 4000;

        //when
        boolean result = orderService.isEnoughMoney(userMonet,orderMoney);

        //then
        Assertions.assertTrue(result);
    }

    @Test
    void isEnoughMoney_WhenReturnFalse() {
        //given
        Integer userMonet =  10000;
        Integer orderMoney = 15000;

        //when
        boolean result = orderService.isEnoughMoney(userMonet,orderMoney);

        //then
        Assertions.assertFalse(result);
    }

    @Test
    void generateOrder() {
    }

    @Test
    void generateOrderError() {
    }
}