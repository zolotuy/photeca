package org.example.photeca.bot;

import org.example.photeca.config.GenericAppConfig;
import org.example.photeca.config.TelegramBotConfig;
import org.example.photeca.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
@Service
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    UserService userService;

    @Autowired
    TelegramBotConfig botConfig;

    @Autowired
    GenericAppConfig appConfig;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            User user = update.getMessage().getFrom();
            if ("/start".equals(messageText)) {
                if (userService.getAllUsers().size() >= appConfig.getMaxUsers()) {
                    sendMessage(chatId, "Bot is not available");
                    return;
                }
                userService.saveUser(chatId, user);
            }
            else if ("/restart".equals(messageText)) {
                userService.refreshPricesForUser(user.getId());
            }
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    public void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
