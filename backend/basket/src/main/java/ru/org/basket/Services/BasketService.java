package ru.org.basket.Services;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.org.basket.Model.Basket;
import ru.org.basket.Model.ProductInfoRequest;
import ru.org.basket.Repositories.BasketRepositories;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Service
@AllArgsConstructor
@Slf4j
public class BasketService {

    private final BasketRepositories basketRepositories;

    public Integer findUserId(String token){

        String urlString = "http://localhost:8060/user/id";
        try {

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization","Bearer " + token);

            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                System.out.println("Response: " + response.toString());

                JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
                return jsonResponse.get("Id").getAsInt();
            } else {
                System.out.println("GET request not worked");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Basket createBasket(ProductInfoRequest productInfoRequest){
        return Basket.builder()
                .productId(productInfoRequest.getProductId())
                .userId(findUserId(
                        productInfoRequest.getToken())
                )
                .build();
    }

    public Basket save(ProductInfoRequest productInfoRequest){
        return basketRepositories.save(createBasket(productInfoRequest));
    }
}
