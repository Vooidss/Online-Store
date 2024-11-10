package com.hukising.telegrambot.Services;

import com.hukising.telegrambot.Bot.Message;
import com.hukising.telegrambot.Bot.TelegramBot;
import com.hukising.telegrambot.DTO.ProductDTO;
import com.hukising.telegrambot.Models.Product;
import com.hukising.telegrambot.Models.ProductStep;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TelegramService {

    private final KafkaProducer kafkaProducer;
    private final ProductDTO productDTO;

    private final Message message;
    public final Map<Long, ProductStep> userSteps = new HashMap<>();
    public final Map<ProductStep, String> products = new HashMap<>();
    public TelegramService(KafkaProducer kafkaProducer, ProductDTO productDTO, Message message) {
        this.kafkaProducer = kafkaProducer;
        this.productDTO = productDTO;
        this.message = message;
    }


    public void addProduct(Long chatId, TelegramBot telegramBot){

        userSteps.put(chatId, ProductStep.TYPE);
        message.sendMessage("""
                Тип продукта:
                """, chatId, null, telegramBot);
    }

    public void handleStep(Long chatId, String input, TelegramBot telegramBot) {
        ProductStep currentStep = userSteps.get(chatId);

        switch (currentStep) {
            case TYPE:
                userSteps.put(chatId, ProductStep.BRAND);
                products.put(ProductStep.TYPE, input);
                message.sendMessage("Введите бренд продукта:", chatId, null, telegramBot);
                break;
            case BRAND:
                userSteps.put(chatId, ProductStep.MODEL);
                products.put(ProductStep.BRAND, input);
                message.sendMessage("Введите модель продукта:", chatId, null, telegramBot);
                break;
            case MODEL:
                userSteps.put(chatId, ProductStep.IMAGE_URL);
                products.put(ProductStep.MODEL, input);
                message.sendMessage("Введите ссылку на картинку продукта:", chatId, null, telegramBot);
                break;
            case IMAGE_URL:
                userSteps.put(chatId, ProductStep.MATERIAL);
                products.put(ProductStep.IMAGE_URL, input);
                message.sendMessage("Введите материал продукта:", chatId, null, telegramBot);
                break;
            case MATERIAL:
                // Сохраняем материал
                userSteps.put(chatId, ProductStep.SIZE);
                products.put(ProductStep.MATERIAL, input);
                message.sendMessage("Введите размеры продукта:", chatId, null, telegramBot);
                break;
            case SIZE:
                userSteps.put(chatId, ProductStep.DESCRIPTION);
                products.put(ProductStep.SIZE, input);
                message.sendMessage("Введите описание для продукта:", chatId, null, telegramBot);
                break;
            case DESCRIPTION:
                userSteps.put(chatId, ProductStep.COLOR);
                products.put(ProductStep.DESCRIPTION, input);
                message.sendMessage("Введите цвет продукта:", chatId, null, telegramBot);
                break;
            case COLOR:
                userSteps.put(chatId, ProductStep.PRICE);
                products.put(ProductStep.COLOR, input);
                message.sendMessage("Введите цену продукта:", chatId, null, telegramBot);
                break;
            case PRICE:
                products.put(ProductStep.PRICE, input);
                userSteps.put(chatId, ProductStep.DISCOUNT);
                message.sendMessage("Введите скидку на продукт:", chatId, null, telegramBot);
                break;
            case DISCOUNT:
                products.forEach((x,y) -> System.out.println(x.getMessage()+ ": " + y));
                userSteps.remove(chatId);
                message.sendMessage("Продукт успешно добавлен!", chatId, null, telegramBot);
                break;
            default:
                message.sendMessage("Неизвестный шаг!", chatId, null, telegramBot);
        }
    }

    public void seeProduct(Long chatId, TelegramBot telegramBot) {
        kafkaProducer.sendMessage(new ProducerRecord<>(
                "telegram",
                "getProduct",
                "getProduct"
        ));

        try{
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        List<Product> products = productDTO.getProducts();

        int countProducts = 0;
        while(countProducts < products.size()){
            message.sendMessage(products.get(countProducts++).toString(),chatId,null,telegramBot);
        }

    }
}
