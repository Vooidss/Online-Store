package com.hukising.telegrambot.Bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class Message {


    public void startMessage(Long chatId, TelegramBot telegramBot) {
        sendMessage("" +
                "Привет, я бот созданыый для удобного взаимодейсвтия с сайтом Hukising. " +
                        "С помощью меня ты можешь добавлять, изменять и смотреть данные."


                , chatId, createReplyButtonMessage(
                List.of("Добавить новый продукт 👟",
                        "Посмотреть все продукты 📙")
        ), telegramBot);
    }

    public void sendMessage(String messageText, Long chatId, ReplyKeyboardMarkup keyboardMarkup, TelegramBot telegramBot) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(messageText);

        if (keyboardMarkup != null) {
            message.setReplyMarkup(keyboardMarkup);
        }

        telegramBot.sendMessage(message);
    }

    public ReplyKeyboardMarkup createReplyButtonMessage(List<String> texts) {

        if(texts.getFirst() == null){
            return null;
        }

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setSelective(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        texts.forEach(text -> keyboard.add(createButton(text)));

        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    private KeyboardRow createButton(String text){
        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton(text));
        return row;
    }
}
