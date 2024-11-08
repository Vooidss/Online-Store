package com.hukising.telegrambot.Bot;

import com.hukising.telegrambot.Services.TelegramService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@RequiredArgsConstructor
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private final Message message;
    private final TelegramService  telegramService;

    private static final String START = "/start";
    private static final String ADD_PRODUCT = "Добавить новый продукт 👟";
    private static final String ADD_PRODUCTS = "Добавить новые продукты 🔜";

    @Value("${bot.name}")
    private String botName;

    @Getter
    @Value("${bot.token}")
    private String botToken;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            String receivedText = update.getMessage().getText();

            if (telegramService.userSteps.containsKey(chatId)) {
                telegramService.handleStep(chatId, receivedText, this);
            } else if(telegramService.decorationProductSteps.containsKey(chatId)){
                telegramService.handleStepForDecorationProduct(chatId, receivedText, this);
            } else {
                switch (receivedText) {
                    case START -> message.startMessage(chatId, this);
                    case ADD_PRODUCT -> telegramService.addProduct(chatId, this);
                    case ADD_PRODUCTS -> telegramService.addProducts(chatId, this);
                    default -> message.sendMessage("Такой команды нет", chatId, null, this);
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    public void sendMessage(SendMessage message){
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения в чат {}: {}", message.getChatId(), e.getMessage());
        }
    }
}
