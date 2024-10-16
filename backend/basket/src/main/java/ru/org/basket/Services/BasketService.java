package ru.org.basket.Services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.org.basket.DTO.ProductInfoRequest;
import ru.org.basket.DTO.ProductResponse;
import ru.org.basket.Model.Basket;
import ru.org.basket.Repositories.BasketRepositories;

@Service
@AllArgsConstructor
@Slf4j
public class BasketService {

    private final BasketRepositories basketRepositories;

    public Integer findUserId(String token) {
        String urlString = "http://localhost:8060/user/id";
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);

            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
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
                System.out.println("Response: " + response.toString());

                JsonObject jsonResponse = JsonParser.parseString(
                    response.toString()
                ).getAsJsonObject();
                return jsonResponse.get("Id").getAsInt();
            } else {
                System.out.println("GET request not worked");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }

    public void deleteBasketsById(Integer userId){
        basketRepositories.deleteAllByUserId(userId);
    }

    private Basket createBasket(ProductInfoRequest productInfoRequest) {
        return Basket.builder()
            .productId(productInfoRequest.getProductId())
            .userId(findUserId(productInfoRequest.getToken()))
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

    public List<ProductResponse> productServiceRequest(Integer userId) throws IOException {

        String urlString = "http://localhost:8071/products/ids";

        List<Integer> list = basketRepositories
                .findProductIdByUserId(userId)
                .stream()
                .map(Basket::getProductId)
                .toList();

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        String jsonInputString = new Gson().toJson(list);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            String response = getResponseAsString(conn);

            return getProducts(response);
        }else{
            log.error("BAD REQUEST");
        }

        return null;
    }

    public Basket save(ProductInfoRequest productInfoRequest) {
        return basketRepositories.save(createBasket(productInfoRequest));
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
                return ResponseEntity.ok(
                        Map.of(
                                "products",
                                products,
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
}
