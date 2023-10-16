package ru.hello_kitties.TG_bot.logic;

import org.telegram.telegrambots.meta.api.objects.Update;

public class BotRequest {
    private String message;

    private String chatId;

    public String getId() {
        return chatId;
    }
    public void setRequest(String req) {
        this.message = req;
    }
    public void setId(String chatId) {
        this.chatId = chatId;
    }

    public String getRequest() {
        return message;
    }
}
