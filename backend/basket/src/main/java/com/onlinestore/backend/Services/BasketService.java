package com.onlinestore.backend.Services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onlinestore.backend.Components.IdComponent;
import jakarta.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.*;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.onlinestore.backend.DTO.BasketResponse;
import com.onlinestore.backend.DTO.ProductInfoRequest;
import com.onlinestore.backend.DTO.ProductResponse;
import com.onlinestore.backend.DTO.ResponseDTO;
import com.onlinestore.backend.Model.Basket;
import com.onlinestore.backend.Model.Status;
import com.onlinestore.backend.Repositories.BasketRepositories;

@Service
@Slf4j
public class BasketService {

    private final BasketRepositories basketRepositories;
    private final IdComponent idComponent;
    private final KafkaProducer kafkaProducer;


    public BasketService(BasketRepositories basketRepositories, IdComponent idComponent, KafkaProducer kafkaProducer) {
        this.basketRepositories = basketRepositories;
        this.idComponent = idComponent;
        this.kafkaProducer = kafkaProducer;
    }

    public Integer findUserId(String token) {
        kafkaProducer.sendMessage(
                new ProducerRecord<>(
                        "basket",
                        "token",
                        token
                )
        );

        int id = idComponent.getUserId();

        if(id != 0 ){
           return id;
        }

        throw new RuntimeException("Пользователь не найден");

    }

    private Basket createBasket(ProductInfoRequest productInfoRequest) {
        return Basket.builder()
            .productId(productInfoRequest.getProductId())
            .userId(findUserId(productInfoRequest.getToken()))
                .countProduct(1)
                .sizeProduct(productInfoRequest.getSizeProduct())
                .status(Status.PROCESS_PURCHASED.getTitle())
            .build();
    }

    private List<ProductResponse> getProducts(String productsToString){

        Type productListType = new TypeToken<
                List<ProductResponse>
                >() {}.getType();

        return new Gson()
                .fromJson(productsToString, productListType);

    }

    private String getResponseAsString(HttpURLConnection conn) throws IOException {
        StringBuilder response = new StringBuilder();

        try (
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream())
                )
        ) {
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        log.info("Response: " + response.toString());

        return response.toString();
    }

    public List<Basket> findBasketByUserId(Integer id){
        return basketRepositories
                .findProductIdByUserId(id);
    }

    public Basket findBasketByUserIdAndProductId(Integer id, Integer productId){
        return basketRepositories.findByUserIdAndProductId(id, productId);
    }

    public Map<Integer,Integer> getBasketIdWithProductId(Integer id, List<Integer> productId){
        return productId.stream()
                .map(product -> findBasketByUserIdAndProductId(id, product))
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(
                        Basket::getProductId,
                        Basket::getId
                ));
    }

    public List<ProductResponse> productServiceRequest(Integer userId) throws IOException {

        Set<Integer> idsProduct = findBasketByUserId(userId)
                .stream()
                .map(Basket::getProductId)
                .collect(Collectors.toSet());

        kafkaProducer.sendMessage(new ProducerRecord<>(
                "basket",
                "ids",
                idsProduct
        ));

        return null;
    }

    public Basket save(ProductInfoRequest productInfoRequest) {
        return basketRepositories.save(createBasket(productInfoRequest));
    }

    public Integer findCountProductById(Integer id){
        return basketRepositories.findCountProductById(id);
    }
    private List<ProductResponse> addBasketIdInProductResponse(List<ProductResponse> products,Map<Integer,Integer> BasketIdWithProductId){
        for (ProductResponse product : products) {
            Integer productId = product.getId();

            if (BasketIdWithProductId.containsKey(productId)) {
                Integer basketId = BasketIdWithProductId.get(productId);

                product.setBasketId(basketId);
                product.setCount(findCountProductById(basketId));
                product.setSize(basketRepositories.findBasketById(basketId).getSizeProduct());

                System.out.println("Product ID: " + productId + " - Basket ID: " + basketId);
            }
        }

        return products;
    }

    public ResponseEntity<Map<String, Object>> getProductsByUser(
        HttpServletRequest request
    ) {

        String token = request.getHeader("Authorization").substring(7);

        int userId;

        try {
            userId = findUserId(token);
        } catch (NullPointerException e) {
            log.info("Проблема с токеном");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of(
                    "message",
                    "Проблема с токеном",
                    "status",
                    HttpStatus.BAD_REQUEST,
                    "code",
                    HttpStatus.BAD_REQUEST.value()
                )
            );
        }

        try {

            List<ProductResponse> products = productServiceRequest(userId);

            if(products != null) {
                Map<Integer,Integer> BasketIdWithProductId = getBasketIdWithProductId(userId, products.stream().map(ProductResponse::getId).toList());
                BasketIdWithProductId.forEach((value,key) -> System.out.println("KEY: "+ key + " - Value: " + value));

                List<ProductResponse> resultProductResponse = addBasketIdInProductResponse(products,BasketIdWithProductId);

                return ResponseEntity.ok(
                        Map.of(
                                "products",
                                resultProductResponse,
                                "status",
                                HttpStatus.OK,
                                "message",
                                "Все в порядке",
                                "code",
                                HttpStatus.OK.value()
                        )
                );
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        Map.of(
                                "status",
                                HttpStatus.NOT_FOUND,
                                "code",
                                HttpStatus.NOT_FOUND.value(),
                                "message",
                                "Корзина пуста"
                        )
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "status",
                    HttpStatus.NOT_FOUND,
                    "code",
                    HttpStatus.NOT_FOUND.value(),
                    "message",
                    "Технические неполадки. Сервис 'Продуктов' не доступен"
                )
            );
        }
    }
    public ResponseEntity<Map<String, Object>> deleteProduct(int id, HttpServletRequest request) {

        String token = request.getHeader("Authorization").substring(7);

        try {
            basketRepositories.deleteBasketByProductIdAndUserId(id,findUserId(token));
        }catch (RuntimeException e){
            log.error(e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of(
                    "status", HttpStatus.NOT_FOUND,
                    "code", HttpStatus.NOT_FOUND.value(),
                    "message", "Проблема с удалением"
            ));
        }

        return ResponseEntity.ok().body(
                Map.of(
                        "status",HttpStatus.OK,
                        "code", HttpStatus.OK.value(),
                        "message", "Продукт удален"
                )
        );
    }

    public ResponseEntity<ResponseDTO<Map<String,Integer>>> updateCountProductInBasket(int id,Integer count) {
        try {
            basketRepositories.updateCountProductById(id,count);

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDTO<>(
                            HttpStatus.OK.toString(),
                            HttpStatus.OK.value(),
                            "Количество продуктов обновлено",
                            null

                    )
            );
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
              new ResponseDTO<>(
                      HttpStatus.NOT_FOUND.toString(),
                      HttpStatus.NOT_FOUND.value(),
                      "Не удалось сохраниться данные",
                      null
              )
            );
        }
    }

    public ResponseEntity<BasketResponse> updateStatus(int id, String status) {
        try{
            log.info(id + " " + status + "ФЫАФЫАФЫАФ");
            basketRepositories.updateStatus(id, status);
        }catch (RuntimeException e){
            log.error(e.getMessage());
            log.error(Arrays.toString(e.getStackTrace()));
            log.error("Произошла ошибка при обновлении статуса");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    BasketResponse
                            .builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .code(HttpStatus.BAD_REQUEST.value())
                            .message("Ошибка при обновлении статуса")
                            .basket(basketRepositories.findBasketById(id))
                            .build()
            );
        }

        return ResponseEntity.ok().body(
                BasketResponse
                        .builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .message("Статус обновлён")
                        .basket(basketRepositories.findBasketById(id))
                        .build()
        );
    }
}
