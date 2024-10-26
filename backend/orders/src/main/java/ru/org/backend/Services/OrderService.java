package ru.org.backend.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.org.backend.DTO.OrderDetails;
import ru.org.backend.DTO.OrderRequest;
import ru.org.backend.DTO.OrderResponse;
import ru.org.backend.Models.Adress;
import ru.org.backend.Models.Order;
import ru.org.backend.Models.Status;
import ru.org.backend.Repositories.OrderRepository;

import java.time.LocalDateTime;

@Service
@Slf4j
@AllArgsConstructor
public class OrderService {

    private final OrderDetails orderDetails;
    private final OrderRepository orderRepository;
    private final AdressService adressService;
    private final KafkaService kafkaService;

    public ResponseEntity<OrderResponse> arrangeOrder(OrderRequest request, HttpServletRequest httpRequest) {

        orderDetails.setOrderRequest(request);

        String token = "";


        log.info("Извлекаем token из заголовка...");

        try {
            token = httpRequest.getHeader("Authorization").substring(7);
        }catch (RuntimeException e){
            log.error("Ошибка извленчения токена");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(OrderResponse
                            .builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .code(HttpStatus.BAD_REQUEST.value())
                            .message("В заголовке не присутствует Authorization или токен")
                            .build());

        }

        log.info("Токен успешно извлечен");
        log.info("Отправляем запрос на получение ID пользователя...");

        kafkaService.getUserId(token);

        if(orderDetails.getUserId() == null){

            orderRepository.save(generateOrderError(Status.REJECTED));

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                OrderResponse
                        .builder()
                        .status(HttpStatus.NOT_FOUND)
                        .code(HttpStatus.NOT_FOUND.value())
                        .message("Пользователь не найден")
                        .build()
                );
        }

        Adress adress = adressService.generateAdress(
                orderDetails
                        .getOrderRequest()
                        .getAdress(),
                orderDetails
                        .getUserId()
        );

        adress = adressService.save(adress);

        log.info("Отправляем запрос на получение количества денег пользователя...");

        kafkaService.getUserMoney(String.valueOf(orderDetails.getUserId()));

        if(orderDetails.getUserMoney() == -1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    OrderResponse
                            .builder()
                            .status(HttpStatus.NOT_FOUND)
                            .code(HttpStatus.NOT_FOUND.value())
                            .message("Ошибка при получении денег")
                            .build()
            );
        }

        log.info("Проверяем хватает ли денег...");

        if(isEnoughMoney(orderDetails.getUserMoney(), orderDetails.getOrderRequest().getResultPrice())){

            log.info("Денег хватает");

            log.info("Производим списывание");

            try {
                kafkaService.writeOffMoney(orderDetails.getUserId(), orderDetails.getOrderRequest().getResultPrice());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        } else{

            log.error("У пользователя не хватает денег");
            saveOrder(generateOrder(Status.REJECTED, adress));

            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(
                    OrderResponse
                            .builder()
                            .status(HttpStatus.PAYMENT_REQUIRED)
                            .code(HttpStatus.PAYMENT_REQUIRED.value())
                            .message("Недостаточно средств.")
                            .build()
            );
        }

        log.info("Деньги списались");
        log.info("сохраняем заказ...");

        Order order = generateOrder(Status.SUCCESSFULLY, adress);

        saveOrder(order);

        log.info("заказ сохранён");
        log.info("удаляем из корзины все товары...");

        kafkaService.deleteAllProductsInBasket(orderDetails.getUserId().toString());

        return ResponseEntity.ok().body(
                OrderResponse
                        .builder()
                        .order(order)
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .message("Заказ оформлен.")
                        .build()
        );
    }

    public void saveOrder(Order order){
        orderRepository.save(order);
    }

    public boolean isEnoughMoney(Integer moneyUser, Integer orderMoney){
        return moneyUser - orderMoney >= 0;
    }
    public Order generateOrder(Status status, Adress adress){
        return Order.builder()
                .userId(orderDetails
                        .getUserId())
                .adressId(adress.getId())
                .nameRecipient(orderDetails
                        .getOrderRequest()
                        .getRecipient()
                        .getName()
                )
                .secondNameRecipient(orderDetails
                        .getOrderRequest()
                        .getRecipient()
                        .getSecondName()
                )
                .phoneRecipient(orderDetails
                        .getOrderRequest()
                        .getRecipient()
                        .getPhone()
                )
                .orderPrice(orderDetails
                        .getOrderRequest()
                        .getOrderPrice()
                )
                .discountPrice(orderDetails
                        .getOrderRequest()
                        .getDiscountPrice()
                )
                .resultPrice(orderDetails
                        .getOrderRequest()
                        .getResultPrice()
                )
                .status(status
                        .getTitle()
                )
                .createdAt(LocalDateTime
                        .now()
                )
                .build();
    }
    public Order generateOrderError(Status status){
        return Order.builder()
                .userId(orderDetails
                        .getUserId())
                .adressId(-1)
                .nameRecipient(orderDetails
                        .getOrderRequest()
                        .getRecipient()
                        .getName()
                )
                .secondNameRecipient(orderDetails
                        .getOrderRequest()
                        .getRecipient()
                        .getSecondName()
                )
                .phoneRecipient(orderDetails
                        .getOrderRequest()
                        .getRecipient()
                        .getPhone()
                )
                .orderPrice(orderDetails
                        .getOrderRequest()
                        .getOrderPrice()
                )
                .discountPrice(orderDetails
                        .getOrderRequest()
                        .getDiscountPrice()
                )
                .resultPrice(orderDetails
                        .getOrderRequest()
                        .getResultPrice()
                )
                .status(status
                        .getTitle()
                )
                .createdAt(LocalDateTime
                        .now()
                )
                .build();
    }

}
