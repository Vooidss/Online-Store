package com.hukising.telegrambot.Services;

import com.hukising.telegrambot.Bot.Message;
import com.hukising.telegrambot.Bot.TelegramBot;
import com.hukising.telegrambot.DTO.ProductDTO;
import com.hukising.telegrambot.Models.Product;
import com.hukising.telegrambot.Models.ProductStep;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class TelegramService {

    private final KafkaProducer kafkaProducer;
    private final ProductDTO productDTO;
    private final Message message;
    public final Map<Long, ProductStep> userSteps = new HashMap<>();
    private final Product product;
    private final SizeService sizeService;
    public TelegramService(KafkaProducer kafkaProducer, ProductDTO productDTO, Message message, Product product, SizeService sizeService) {
        this.kafkaProducer = kafkaProducer;
        this.productDTO = productDTO;
        this.message = message;
        this.product = product;
        this.sizeService = sizeService;
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
                product.setType(input);
                message.sendMessage("Введите бренд продукта:", chatId, null, telegramBot);
                break;
            case BRAND:
                userSteps.put(chatId, ProductStep.MODEL);
                product.setBrand(input);
                message.sendMessage("Введите модель продукта:", chatId, null, telegramBot);
                break;
            case MODEL:
                userSteps.put(chatId, ProductStep.IMAGE_URL);
                product.setModel(input);
                message.sendMessage("Введите ссылку на картинку продукта:", chatId, null, telegramBot);
                break;
            case IMAGE_URL:
                userSteps.put(chatId, ProductStep.MATERIAL);
                product.setImg(input);
                message.sendMessage("Введите материал продукта:", chatId, null, telegramBot);
                break;
            case MATERIAL:
                userSteps.put(chatId, ProductStep.SIZE);
                product.setMaterial(input);
                message.sendMessage("Введите размеры продукта:", chatId, null, telegramBot);
                break;
            case SIZE:
                userSteps.put(chatId, ProductStep.DESCRIPTION);
                product.setSizes(sizeService.convertStringInSize(input));
                message.sendMessage("Введите описание для продукта:", chatId, null, telegramBot);
                break;
            case DESCRIPTION:
                userSteps.put(chatId, ProductStep.COLOR);
                product.setDescription(input);
                message.sendMessage("Введите цвет продукта:", chatId, null, telegramBot);
                break;
            case COLOR:
                userSteps.put(chatId, ProductStep.PRICE);
                product.setColor(input);
                message.sendMessage("Введите цену продукта:", chatId, null, telegramBot);
                break;
            case PRICE:
                product.setPrice(Integer.parseInt(input));
                userSteps.put(chatId, ProductStep.DISCOUNT);
                message.sendMessage("Введите скидку на продукт:", chatId, null, telegramBot);
                break;
            case DISCOUNT:
                product.setDiscount(Integer.parseInt(input));
                addProductsInDataBase(product);
                userSteps.remove(chatId);
                message.sendMessage("Продукт успешно добавлен!", chatId, null, telegramBot);
                break;
            default:
                message.sendMessage("Неизвестный шаг!", chatId, null, telegramBot);
        }
    }

    public void addProductsInDataBase(Product products){
        System.out.println(products.toString());
    }
    public void seeProduct(Long chatId, TelegramBot telegramBot) {
        try{
            kafkaProducer.sendMessage(new ProducerRecord<>(
                    "telegram",
                    "getProduct",
                    "getProduct"
            ));
        }catch (KafkaException e){
            log.error("Проблема с kafka. Нельзя отправить сообщение.");
            log.error(e.getMessage());
            log.error(Arrays.toString(e.getStackTrace()));
            message.sendMessage("Произошла ошибка 😓. Попробуйте позже.",chatId,null,telegramBot);
            throw new KafkaException("Проблема с kafka. Нельзя отправить сообщение.");
        }

        message.sendMessage("Немного подождите...",chatId,null,telegramBot);

        try{
            Thread.sleep(2000);
            if(productDTO.getProducts() == null){
                Thread.sleep(28000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            List<Product> product = productDTO.getProducts();
            int countProducts = 0;
            while(countProducts < product.size()){
                message.sendMessage(product.get(countProducts++).toString(),chatId,null,telegramBot);
            }
        } catch (NullPointerException e){
            log.error("Product пустой.");
            log.error(e.getMessage());
            message.sendMessage("Произошла ошибка 😓. Попробуйте позже.",chatId,null,telegramBot);
        }
    }
}
